#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "test_code.h"
#include "edge_crypto.h"
#include "Hcrypto.h"
#ifdef _WIN32
#include <windows.h>
#else
#include <stdarg.h>
#endif
#define ONE_BLOCK_SIZE 16
#define MAX_SIZE 2048 
#define H_CBC 1
#define H_OFB 2
const char *
crypto_getStatusString(int p_status)
{
        switch (p_status) {
        case EDGE_CRYPTO_STATUS_NOT_INITIALIZED:
                return "NOT INITIALIZED";
        case EDGE_CRYPTO_STATUS_CMVP:
                return "CMVP MODE";
        case EDGE_CRYPTO_STATUS_NORMAL:
                return "NonCMVP MODE";
        case EDGE_CRYPTO_STATUS_ERROR:
                return "ERROR";
        }
        return "ERROR";
}
void hexdump(const char *title, void *mem, unsigned int len)
{
    unsigned int i = 0;
    unsigned int j = 0;
    fprintf(stdout, "-[%s](%d)\n", title, len);
    for (i = 0; i<len + ((len % 16) ? (16 - len % 16) : 0); i++)
    {
        // print offset
        if (i % 16 == 0)
            printf("0x%08X: ", i);
        // print hex data
        if (i<len)
            printf("%02X ", 0xFF & ((char*)mem)[i]);
        else
            printf("   ");
        // print ascii dump
        if (i % 16 == (16 - 1))
        {
            for (j = i - (16 - 1); j <= i; j++)
            {
                if (j >= len)
                    putchar(' ');
                else if ((((char*)mem)[j] >= 32) && (((char*)mem)[j] <= 126))
                    putchar(0xFF & ((char*)mem)[j]);
                else
                    putchar('.');
            }
            putchar('\n');
        }
    }  // for
    puts("");
}
void printf_red(const char *const Format, ...) {
    va_list valist;
    va_start(valist, Format);
        char buff[2048];
#ifdef _WIN32
    HANDLE h = GetStdHandle(STD_OUTPUT_HANDLE);
    WORD old;
    CONSOLE_SCREEN_BUFFER_INFO csbiInfo;
    GetConsoleScreenBufferInfo(h, &csbiInfo);
    old = csbiInfo.wAttributes;
    SetConsoleTextAttribute(h, FOREGROUND_RED | FOREGROUND_INTENSITY);
    vprintf(Format, valist);
    SetConsoleTextAttribute(h, old);
#else
    printf("\033[0;91m");
    vsprintf(buff, Format, valist);
        printf (buff);
    printf("\033[0m");
#endif
    va_end(valist);
}
void print_result(const char * p_function, int p_result)
{
        if (p_result != 0) {
                printf_red("%s fail [%d]\n", p_function, p_result);
        }
        else {
                printf("%s success [%d]\n", p_function ,p_result);
        }
}