#include <stdio.h>
#include "edge_crypto.h"
void        hexdump                             (const char *title, void *mem, unsigned int len);
void        printf_red                          (const char *const Format, ...);
void        print_result                        (const char * p_function, int p_result);
int         chk_invalid_val                     (int cipher_id, uint32_t key_len, uint32_t iv_len);
int         init_update_final_string            ();
int         init_update_final_file              ();
int         test_main                           ();
const char* crypto_getStatusString              (int p_status);
