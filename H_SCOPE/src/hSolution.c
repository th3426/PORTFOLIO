#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hSolution.h"

int H_solution ()
{
    int ret = 0;

    printf("========  [ START DECOMPILE APP ]  ========\n");
    ret = system ("apktool d ../target_apk/origin_2048.apk -o ../target_apk/de_2048");
    if (ret != 0)
	{
        printf("fail decompile\n");
        return ret;
    }

    printf("========  [ START rm / cp FILES ]  ========\n");
    ret = inject_code();
    if(ret != 0)
	{
        printf("fail inject\n");
        return ret;
    }

    printf("========  [ START RECOMPILE APP ]  ========\n");
    ret = system("apktool b ../target_apk/de_2048 -o ../target_apk/re_2048.apk");
    if(ret != 0)
	{
        printf("fail recomile\n");
        return ret;
    }

    printf("========  [ START RESIGNING APP ]  ========\n");
	ret = system("apksigner sign --ks ../target_apk/h_server.keystore --ks-key-alias alias_name --out ../target_apk/re_2048_signed.apk ../target_apk/re_2048.apk");
    if(ret != 0)
	{
        printf("fail resigning\n");
        return ret;
    }
}

int inject_code()
{
    int ret = 0;

    ret = system("rm ../target_apk/de_2048/Androidmanifest.xml");
    if(ret != 0)
	{
        printf("fail rm androidmanifest file\n");
        return 0;
    }
	printf("--> rm Androidmanifest xml\n");

    ret = system("cp ../inject_code/Androidmanifest.xml ../target_apk/de_2048");
    if(ret != 0)
	{
        printf("fail cp androidmanifest file\n");
        return 0;
    }
	printf("--> cp Androidmanifest xml\n");

    ret = system("cp ../inject_code/hSolution* ../target_apk/de_2048/smali/com/tpcstld/twozerogame/");
    if(ret != 0)
	{
        printf("fail cp H_solution file\n");
        return 0;
    }
	printf("--> inject smali code\n");

	ret = system("jar cvf inject_assets.jar ../inject_lib/assets");
	if(ret != 0)
	{
		printf("fail zip jar\n");
		return 0;
	}
	printf("--> zip jar assets\n");

	ret = system("mkdir ../target_apk/de_2048/lib");
    if(ret != 0)
	{
		printf("fail make dir\n");
		return 0;
	}
	printf("--> make dir\n");

	ret = system("cp -r ../inject_lib/assets/* ../target_apk/de_2048/lib");
	if(ret != 0)
	{
		printf("fail cp assets\n");
		return 0;
	}
	printf("--> cp assets\n");

	ret = system("mv inject_assets.jar ../target_apk/de_2048/assets/");
	if(ret != 0)
	{
		printf("fail mv jar file\n");
		return 0;
	}
	printf("--> mv jar file\n");

    return ret;
}
