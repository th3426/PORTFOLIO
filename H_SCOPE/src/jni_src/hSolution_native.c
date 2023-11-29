#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <android/log.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/select.h>
#include <errno.h>
#include <pthread.h>
#include "hSolution_native.h"
#include "hSolution_error.h"
#include "edge_crypto.h"

#define BUF_SIZE 2048
#define KEY_LEN  2048

JNIEXPORT void JNICALL Java_com_tpcstld_twozerogame_hSolution_errorPrint (JNIEnv *env, jobject obj, jint ret, jint socket_num)
{
    __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib", "!ERR, socket : %d, err_code : %d", socket_num, ret);
    close(socket_num);
    exit(1);
}

JNIEXPORT jint JNICALL Java_com_tpcstld_twozerogame_hSolution_verifyIntegrity (JNIEnv * env, jobject obj, jstring signData)
{
    int  ret = 0;
    int  my_sock = 0;
    char name[20] = "user";

    struct sockaddr_in server_addr;

    char* sData = (*env)->GetStringUTFChars(env, signData, NULL);

    ///////////////////////////////////////////////
    EDGE_CIPHER_PARAMETERS symmParam;
    int   cipher_id = EDGE_CIPHER_ID_ARIA128;

    // 서버로 부터 복호화할 대칭키
    uint8_t     symm_key[17]              = { 0x00, };
    uint32_t    symm_keylength            = 16;

    uint8_t  iv[16] = { 0x00, };
    uint32_t iv_length = 16;

    memset(&symmParam, 0, sizeof(EDGE_CIPHER_PARAMETERS));

    symmParam.m_mode = EDGE_CIPHER_MODE_CBC;
    symmParam.m_padding = EDGE_CIPHER_PADDING_PKCS5;

    memcpy(symmParam.m_modeparam.m_iv, iv, iv_length);
    symmParam.m_modeparam.m_ivlength = iv_length;
    ///////////////////////////////////////////////

    // 키 생성
    EDGE_ASYM_KEY_PARAM key_param;
    EDGE_ASYM_ENC_PARAM param;

    // 공개키 / 개인키
    uint8_t     public_key[KEY_LEN]             = { 0x00, };
    uint32_t    public_keylength                = 0;
    uint8_t     private_key[KEY_LEN]            = { 0x00, };
    uint32_t    private_keylength               = 0;

    // 서버로 부터 받아올 암호문
    uint8_t     input[BUF_SIZE]                 = { 0x00, };
    uint32_t    input_length                    = 0;

    uint8_t     BUF[BUF_SIZE]                   = { 0x00 , };
    uint32_t    BUF_length                      = 0;

    memset(&key_param, 0, sizeof(EDGE_ASYM_KEY_PARAM));
    key_param.m_algorithm = EDGE_ASYM_ID_RSA;
    key_param.rsa.m_bits = 2048;
    key_param.rsa.m_exponent = 65537;
    
    param.m_encMode = EDGE_RSA_ENC_MODE_OAEP;
    param.m_oaep.m_hashAlg = EDGE_HASH_ID_SHA256;
    param.m_oaep.m_label = NULL;
    param.m_oaep.m_labelLength = 0;
    param.m_oaep.m_mgfHashAlg = EDGE_HASH_ID_SHA256;
    
    // 키 생성
    __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib", "make key pair");
    ret = edge_asym_gen_keypair(public_key, &public_keylength, private_key, &private_keylength, &key_param);
    if(ret != 0) {
        ret = HSOLUTION_ERR_ASYM_GEN_KEY_PAIR;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }

    // 1. socket()
    // ! 매니페스트에서 권한 필요, 없을 시 return -1
    ret = socket(AF_INET, SOCK_STREAM, 0);
    if(ret == -1) {
        ret = HSOLUTION_ERR_SOCK_ERR;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }
    else{
        __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib", "make socket");
        my_sock = ret;
    }

    memset(&server_addr, 0, sizeof(server_addr));
    // AF_INET : Address Family Internet, ipv4
    server_addr.sin_family = AF_INET;
    // ip주소를 넘기면 빅엔디안 32비트 unsigned long으로 변경
    server_addr.sin_addr.s_addr = inet_addr("10.1.31.43");
    // short int 데이터를 네트워크 byte order로 변경
    server_addr.sin_port = htons(atoi("3000"));
    
    sprintf(name, "[%s]", "Phone");

    // 2. connect()
    ret = connect(my_sock, (struct sockaddr*) &server_addr, sizeof(server_addr));
    if(ret != 0) {
        ret = HSOLUTION_ERR_CONNECT_ERR;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }
    else {
        __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib", "%s is connected", name);
    }

    // 연결에 성공하면, 서버에 클라이언트의 공개키를 보냄
    ret = send(my_sock, public_key, public_keylength, 0);
    if(ret == -1) {
        ret = HSOLUTION_ERR_SEND_ERR;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }
    // 서버로부터 암호화된 대칭키를 받아옴
    ret = recv(my_sock, input, BUF_SIZE, 0);
    if(ret == -1) {
        ret = HSOLUTION_ERR_RECV_ERR;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }
    else{
      input_length = ret;
      __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib","recv from server enc symm key..");
    }

    // 암호화된 대칭키를 복호화하여 대칭키 획득
    ret = edge_asym_dec(private_key, private_keylength, &param, input, input_length, symm_key, &symm_keylength);
    if(ret != 0) {
        ret = HSOLUTION_ERR_ASYM_DEC;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }
    else{
        __android_log_print(ANDROID_LOG_DEBUG,"hSolution_native_lib","get symm key");
    }
    memset(input, 0, input_length);

    memcpy(input, sData, 40);
    input_length = 40;

    ret = edge_enc(cipher_id, symm_key, symm_keylength, &symmParam, input, input_length, BUF, &BUF_length);
    if(ret != 0){
        ret = HSOLUTION_ERR_SYMM_ENC;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }

    __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib", "encrypt data");

    ret = send(my_sock, BUF, BUF_length, 0);
    if(ret == -1){
        ret = HSOLUTION_ERR_SEND_ERR;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }
    else{
        __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib", "success send enc data");
    }

    memset(input, 0x00, BUF_SIZE);
    input_length = 0;
    memset(BUF, 0x00, BUF_SIZE);
    BUF_length = 0;

    ret = recv(my_sock, input, BUF_SIZE, 0);
    if(ret == -1){
        ret = HSOLUTION_ERR_RECV_ERR;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }
    input_length = ret;

    ret = edge_dec(cipher_id, symm_key, symm_keylength, &symmParam, input, input_length, BUF, &BUF_length);
    if(ret != 0){
        ret = HSOLUTION_ERR_SYMM_DEC;
        Java_com_tpcstld_twozerogame_hSolution_errorPrint(env, obj, ret, my_sock);
    }

    ret = memcmp("VERIFY SUCCESS", BUF, 14);
    if(ret == 0){
        close(my_sock);
        return HSOLUTION_SUCCESS;
    }
    else{
        close(my_sock);
        return HSOLUTION_ERR_VERIFY_ERR;
    }
}

JNIEXPORT jint JNICALL Java_com_tpcstld_twozerogame_hSolution_InitCrypto (JNIEnv* env, jobject obj, jstring _signPath) {
    int ret = 0;
    char* signPath= (*env)->GetStringUTFChars(env, _signPath, NULL);

    ret = edge_crypto_init(signPath);

    if(ret != 0){
        __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib", "!ERR, err_code : %d", ret);
    }
    else{
        __android_log_print(ANDROID_LOG_DEBUG, "hSolution_native_lib", "success init");
    }
    return ret;
}