#include <stdio.h>

// #================  ECB ENC, DEC  =================#
int ecb_enc (int       cipher_id,                                      // [in]
		     unsigned char * p_key,    unsigned int p_key_len,         // [in]
			 unsigned char * p_input,  unsigned int p_input_len,       // [in]
			 unsigned char * c_output, unsigned int * c_output_len);   // [out]

int ecb_dec (int       cipher_id,                                      // [in]
		     unsigned char * p_key,    unsigned int p_key_len,         // [in]
			 unsigned char * c_input,  unsigned int c_input_len,       // [in]
			 unsigned char * p_dec,    unsigned int * p_dec_len);      // [out]


// #================   CBC ENC, DEC  =================#
int cbc_enc (int       cipher_id,                                      // [in]
		     unsigned char * p_key,    unsigned int p_key_len,         // [in]
			 unsigned char * p_input,  unsigned int p_input_len,       // [in]
			 unsigned char * c_output, unsigned int * c_output_len);   // [out]

int cbc_dec (int       cipher_id,                                      // [in]
		     unsigned char * p_key,    unsigned int p_key_len,         // [in]
			 unsigned char * c_input,  unsigned int c_input_len,       // [in]
			 unsigned char * p_dec,    unsigned int * p_dec_len);      // [out]


// #=============  INIT_UPDATE_FINAL   ===============#
typedef struct ctx H_CONTEXT;

H_CONTEXT* make_ctx();

void reset_ctx(H_CONTEXT* ctx);

void free_ctx(H_CONTEXT* ctx);

int enc_init(H_CONTEXT* ctx, const int cipher_id,                                     // [in]
		     unsigned char* key, unsigned int key_len,                                // [in]
			 const unsigned char* iv, const unsigned int iv_len);                     // [in]

int enc_update(H_CONTEXT* ctx, unsigned char* p_input, unsigned int p_input_len,      // [in]
		                       unsigned char* c_output, unsigned int* c_output_len);  // [out]

int enc_final(H_CONTEXT* ctx,                                                         // [in]
		      unsigned char* c_output, unsigned int* c_output_len);                   // [out]

int dec_init(H_CONTEXT* ctx, const int cipher_id,                                     // [in]
		     unsigned char* key, unsigned int key_len,                                // [in]
			 const unsigned char* iv, const unsigned int iv_len);                     // [in]

int dec_update(H_CONTEXT* ctx, unsigned char* c_input, unsigned int c_input_len,      // [in]
		                       unsigned char* p_output, unsigned int* p_output_len);  // [out]

int dec_final(H_CONTEXT* ctx,                                                         // [in]
		      unsigned char* p_output, unsigned int* p_output_len);                   // [out]
