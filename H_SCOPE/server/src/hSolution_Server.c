#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <pthread.h>
#include "edge_crypto.h"
#include "Hcrypto.h"

#define BUF_SIZE 2048 
#define MAX_CLIENT 5 
#define KEY_LEN 2048

void  error_print  (char* message);
void* client_read  (void* arg);
void  client_write (int client_sock, char* msg, int msg_len);

// <SERVER>
// 1. socket()          main thread
// 2. bind()            main thread
// 3. listen()          main thread
// 4. accept()          main thread , and make new thread
// 5. read()/write()    use thread
// 6. close             detach thread

// thread에서 사용할 전역변수
// 총 클라이인트 수
int client_count = 0;
// 클라이언트의 소켓 배열
int client_sockets[MAX_CLIENT] = { 0, };

pthread_mutex_t mutx;

int main(int argc, char *argv[]){
	int ret = 0;
	int server_sock = 0;
	int tmp_client_sock = 0;
	int option = 0;

	pthread_t tmp_id;

	struct sockaddr_in server_addr, client_addr;
	socklen_t client_addr_size;

	EDGE_ASYM_ENC_PARAM param;

	///////////////////////////////////////////////
	//          암호(대칭, 비대칭) 관련          //
	///////////////////////////////////////////////
    EDGE_CIPHER_PARAMETERS symmParam;
    int   cipher_id = EDGE_CIPHER_ID_ARIA128;

    uint8_t  iv[16] = { 0x00, };
    uint32_t iv_length = 16;

    memset(&symmParam, 0, sizeof(EDGE_CIPHER_PARAMETERS));

    symmParam.m_mode = EDGE_CIPHER_MODE_CBC;
    symmParam.m_padding = EDGE_CIPHER_PADDING_PKCS5;

    memcpy(symmParam.m_modeparam.m_iv, iv, iv_length);
    symmParam.m_modeparam.m_ivlength = iv_length;


    ///////////////////////////////////////////////
	//        서버 키스토어 세팅 값 관련         //
	///////////////////////////////////////////////
	FILE * settingFile = NULL;
	char sha1fingerprint[1024] = { 0x00, };


	// 클라이언트의  공개키
	uint8_t     client_public_key[KEY_LEN]       = { 0x00, };
	uint32_t    client_public_keylength          = 0;
	
	// 대칭키
	uint8_t     symm_key[17]              = "test text sample";
	uint32_t    symm_keylength            = 16;
	
	// 암호화된 대칭키
	uint8_t     output[512]               = { 0x00, };
	uint32_t    output_length             = 0;

	uint8_t		buf[BUF_SIZE] = { 0x00, };
	uint32_t	buf_length = 0;

	char        *signPath                 = NULL;
	void*       thr_ret                   = NULL;

	ret = system("keytool -v -list -keystore ../../target_apk/h_server.keystore");
	if( ret != 0){
		printf("\n\nERR invalid pw\n\n");
		return 0;
	}

	settingFile = fopen("./.SETTING", "rb");
	if(settingFile == NULL){
		printf("fopen fail\n");
	}

	char line[100];

	// 한줄씩 읽어와 SHA1: 형식에 맞으면 데이터를 가져옴
	// 이후 SHA1 이외에 SHA256이 도입되더라도 응용&수정 가능
	while(fgets(line, sizeof(line), settingFile)!= NULL){
    	if(strncmp(line, "SHA1:", 5) == 0){
        	sscanf(line, "SHA1:%s", sha1fingerprint);
	        break;
    	}
	}

	printf("SETTING VALUE : sha1 :[ %s ]\n", sha1fingerprint);

	printf("crypto init");	
	ret = edge_crypto_init(signPath);
	if (ret != 0) {
	    printf("\tfail [%d]\n", ret);
	    return ret;
	}
	printf("\tsuccess..\n");

	param.m_encMode = EDGE_RSA_ENC_MODE_OAEP;
	param.m_oaep.m_hashAlg = EDGE_HASH_ID_SHA256;
	param.m_oaep.m_label = NULL;
	param.m_oaep.m_labelLength = 0;
	param.m_oaep.m_mgfHashAlg = EDGE_HASH_ID_SHA256;

	if(argc!=2){
		printf("Usage : %s <port>\n", argv[0]);
		exit(1);
	}

	// 1. 소켓 생성, server의 소켓은 socket에서 생성된다
	// fd값은 3번부터 사용자가 할당한 값이 나온다.
	// 0, 1, 2번은  시스템이 사용 (0 : stdin / 1 : stdout / 2 : stderr)
	// 해당 프로세스의 fd값중 사용하지 않는 가장 작은 값을 할당해준다
	// AF_INET == PF_INET (address family, protocol family 둘다 같은 값, AF 사용 권장

	printf("make socket");
	ret = socket(AF_INET/* = PF_INET*/, SOCK_STREAM, 0);
	if(ret == -1)
		error_print("\terror\n");
	else
		printf("\tsuccess\n");
	server_sock = ret;
	option = 1;
	// 소켓의 옵션을 REUSEADDR로 변경, 같은 포트에 대해 다른 소켓을 허용
	setsockopt(server_sock, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option));

	memset(&server_addr, 0, sizeof(server_addr));
	// AF_INET : Address Family Internet, ipv4
	server_addr.sin_family = AF_INET;
	// htonl : 빅엔디안으로 네트워크 바이트 정렬, unsigned long
	// INADDR_ANY : 가능한 어떤 ip주소 수용
	server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	// htons : unsigned short
	server_addr.sin_port = htons(atoi(argv[1]));

	
	// 2. 소켓과 서버 주소 bind
	printf("bind");
	ret = bind(server_sock, (struct sockaddr*)&server_addr, sizeof(server_addr));
	if(ret == -1)
		error_print("\t\terror\n");
	else
		printf("\t\tsuccess\n");
	
	// 3. 서버 listen
	ret = listen(server_sock, MAX_CLIENT);
	if(ret == -1)
		error_print("listen error\n");
	else
		printf("now server listening..\n\n");

	while(1){
		client_addr_size = sizeof(client_addr);

		// 4. 요청에 대해 accept, client의 소켓은 accept에서 생성된다
		ret = accept(server_sock, (struct sockaddr*)&client_addr, &client_addr_size);
		if(ret == -1) 
			error_print("accept error\n");

		else{
			tmp_client_sock = ret;

			pthread_mutex_lock(&mutx);
			client_sockets[client_count] = tmp_client_sock;
			client_count++;
			pthread_mutex_unlock(&mutx);

			// 4-1. accept 성공, 클라이언트 소켓 할당
			printf("[in]\tconnected client[%d]\n", tmp_client_sock);
			
			// 클라이언트로부터 공개키를 받아옴
			ret = recv(tmp_client_sock, client_public_key, BUF_SIZE, 0);
			if(ret == -1)
				printf("fail to read client public key\n");
			else{
				client_public_keylength = ret;
				printf("[in]\trecv client public key from client[%d]..\n",tmp_client_sock);
			}

			// 클라이언트의 공개키로 대칭키를 암호화
			ret = edge_asym_enc(client_public_key, client_public_keylength, &param, symm_key, symm_keylength,
					                                                                output, &output_length);
			if(ret != 0)
				error_print("fail to enc symm key by client public key\n");
			else
				printf("[in]\tenc symm key use client public key..\n");

			// 클라이언트에게 암호화된 대칭키를 보냄
			ret = send(tmp_client_sock, output, output_length, 0);
			if(ret == -1)
				error_print("faile to write enc output to client\n");		
			else
				printf("[in]\tsend enc symm key to client[%d]..\n", tmp_client_sock);
			
			memset(output, 0, output_length);
			output_length = 0;

			ret = recv(tmp_client_sock, buf, BUF_SIZE, 0);
			buf_length = ret;
			if(ret == -1 ){
				printf("fail test\n");
			}
			else{
				printf("[in]\trecv data, length : %d\n", ret);
				ret = edge_dec(cipher_id, symm_key, symm_keylength, &symmParam, buf, buf_length, output, &output_length);
				if(ret != 0){
					printf("err fail dec\n");
				}
				else{
					ret = memcmp(sha1fingerprint, output, output_length);
					memset(output, 0x00, output_length);
					output_length = 0;
					
					if(ret == 0){
						printf("[in]\tsign match\n");
						ret = edge_enc(cipher_id, symm_key, symm_keylength, &symmParam, "VERIFY SUCCESS", 14, output, &output_length);
						// ERROR 
					}
					else{
						printf("[in]\tsign unmatch\n");
						ret = edge_enc(cipher_id, symm_key, symm_keylength, &symmParam, "VERIFY FAIL", 11, output, &output_length);
						// ERROR
					}
					ret = send(tmp_client_sock, output, output_length, 0);
					// ERROR
				}
			}
			//pthread_create(&tmp_id, NULL, client_read, (void*)&tmp_client_sock);
		
			//pthread_detach(tmp_id);
		}
	}
	close(server_sock);
	return 0;
}

void error_print(char *message){
	fputs(message, stderr);
	exit(1);
}

void* client_read(void* arg){
	// void로 입력받았기 때문에 int로 형변환 시켜줌
	int tmp_client_sock = *((int*)arg);
	int msg_len = 0;
	char msg[BUF_SIZE] = "";

	// while문을 빠져나온건 접속이 끊어진 것
	while((msg_len = recv(tmp_client_sock, msg, BUF_SIZE, 0)) > 0)
		// write 진행
		client_write(tmp_client_sock ,msg, msg_len);

	// 접속이 종료된 경우
	printf("[out]\tdisconnected client[%d]\n", tmp_client_sock);

	pthread_mutex_lock(&mutx);
	// 현재 tmp_client_sock에 할당된 소켓을 해제해야함
	for(int i = 0; i < client_count; i++){
		// 현재 tmp_client_sock에 해당되는 fd 찾기
		// 소켓배열에서 연결이 끊긴 해당 소켓이 있는 i를 찾아감
		if(tmp_client_sock == client_sockets[i]){
			for(int j = i; j < client_count - 1; j++)
				client_sockets[j] = client_sockets[j+1];
			client_count--;
			close(tmp_client_sock);
			break;
		}
	}
	pthread_mutex_unlock(&mutx);
}

void client_write(int client_sock, char* msg, int msg_len){
	int ret = 0;

	// 전역 변수 사용
	pthread_mutex_lock(&mutx);
	for(int i = 0; i < client_count; i++){
		if(client_sockets[i] != client_sock){
			ret = send(client_sockets[i], msg, msg_len, 0);
			if(ret == -1)
				error_print("fail func client write\n");
		}
	}
	pthread_mutex_unlock(&mutx);
}
