#include <stdio.h>
#define MAX 2048
int main() {
    int n,bt=MAX;
    scanf("%d",&n);
    while(bt>0){
        printf("%d",n/bt);
        n%=bt;
        bt/=2;
    }
    return 0;
}
