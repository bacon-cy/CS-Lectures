#include <stdio.h>
#include <ctype.h>
#include <string.h>
char key[1003],data[10003];
int keyLen,dataLen;
int n;
int main() {
    fgets(key,1002,stdin);
    fgets(data,10002,stdin);
    keyLen= (int)strlen(key);
    dataLen= (int)strlen(data);
    key[keyLen]='\0';
    data[dataLen]='\0';
    keyLen--;
    dataLen--;
    for(int i=0;i<dataLen;i++){
        data[i]=data[i]^key[i%keyLen];
        printf("%c",data[i]);
    }

    return 0;
}
