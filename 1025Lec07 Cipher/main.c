#include <stdio.h>
#include <string.h>
int k;
char a,qq;
void aUpperEncode(){
    if(a+k >'Z')a+=k-26;
    else a+=k;
}
void aLowerEncode(){
    if(a+k >'z')a+=k-26;
    else a+=k;
}
void dUpperEncode(){
    if(a+k >'Z')a+=k-26;
    else a+=k;
}
void dLowerEncode(){
    if(a+k >'z')a+=k-26;
    else a+=k;
}
int main() {
    scanf("%d",&k);
    getchar();
    qq=getchar();
    while(k<0) k+=26;
    while(k>26) k-=26;
    getchar();
    while(1){
        a = getchar();
        if(a == '\n') break;
        if(qq='a'){
            if(a >= 'A' && a <= 'Z') aUpperEncode();
            else if(a >= 'a' && a <= 'z') aLowerEncode();
        }
        else{
            if(a >= 'A' && a <= 'Z') dUpperEncode();
            else if(a >= 'a' && a <= 'z') dLowerEncode();
        }
        printf("%c",a);
    }
    return 0;
}
