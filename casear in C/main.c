#include <stdio.h>
#include <string.h>
#define MAX 1096
char c[MAX]="";

void caesar(){
    for(int i=0;i<strlen(c);i++){
        if((c[i]+1)<='Z') c[i]++;
        else c[i]+=1-26;
    }
}

void ascii_caesar(int len){
    for(int i=0;i<strlen(c);i++){
        if((c[i]+1)<=127) c[i]++;
        else c[i]+=1-127;
    }
}

int main() {
    fgets(c,MAX,stdin);
    c[strlen(c)]='\0';
    for(int i=0;i<=2;i++){
        caesar(i);
        printf("%s\n",c);
        printf("-------\n");
    }
    return 0;
}
