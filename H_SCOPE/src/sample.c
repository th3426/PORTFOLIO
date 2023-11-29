#include <stdio.h>
#include "hSolution.h"

/* ————————————————————————————————————————————————————— *
 * 1. 별도 진입점 클래스 개발 없는 경우
 *   1-1. manifest 에 별도 진입점이 없는 경우
 *     ==> manifest 에 AppIronStarter 주입
 *   1-2. manifest 에 진입점이 androidx/multidex/aa(혹은 android/support/multidex/MultiDexApplication) 인 경우
 *     ==> manifest 에 AppIronStarter 주입
 *
 * 2. 별도 진입점 클래스 개발이 있는 경우
 *   2-1. Application 을 상속 받아서 개발한 경우
 *     ==> Application 을 상속 받은 원본 진입점의 슈퍼 클래스를 AppIronStarter 로 변경
 *   2-2. MultiDexApplication 을 상속 받아서 개발한 경우
 *     ==> MultiDexApplication 을 상속 받은 원본 진입점의 슈퍼 클래스를 AppIronStarter 로 변경
 *     ==> AppIronStarer 의 슈퍼 클래스를 MultiDexApplication 으로 변경
* ————————————————————————————————————————————————————— */

int main ()
{
    int ret = 0;

    ret = H_solution();
    if(ret != 0)
    {
        printf("H_solution failed\tret : [%d]\n", ret);
        return ret;
    }
	printf("H_solution success\n");

    return ret;
}
