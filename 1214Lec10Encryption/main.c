#include <stdio.h>
#include <assert.h>
#define N 64
int type;
char c[64];
char keep[64];
void swap(int l,int r);
void rotate(int dig,int l,int r); //dig:向left, rotate right n = rotate left 64-n
void rail_fence(char key[],int key_len,int l,int r);
void caesar(int key,int l,int r);
void trithemius(int inde,int key,int l,int r);//increase inde=1; decrease inde=0
void vigenere(char key[],int key_len,int l,int r);
void first();void second();void third();void forth();void fifth();
int main() {
    scanf("%d",&type);
    getchar();
    for(int i=0;i<N;i++)c[i]=getchar();
    switch(type){
        case 1:
            first();break;
        case 2:
            second();break;
        case 3:
            third();break;
        case 4:
            forth();break;
        case 5:
            fifth();break;
        default:
            rotate(57,0,63);
    }
    for(int i=0;i<N;i++)printf("%c",c[i]);
    return 0;
}
void first(){
    swap(0,63);
    caesar(13,0,31);
    vigenere("meow",4,32,63);
    rotate(3,0,63);
}
void second(){//ok
    rotate(53,0,63);
    trithemius(1,74,0,63);
    rotate(63,0,31);
    rotate(61,32,63);
    rail_fence("43127658",8,0,63);
}
void third(){//ok
    rail_fence("3241",4,0,63);
    caesar(8,0,31);
    rail_fence("18436572",8,32,63);
    swap(0,63);
    trithemius(0,4,0,63);
    rotate(57,0,63);
}
void forth(){
    vigenere("vigenere",8,0,31);
    swap(0,31);
    trithemius(1,3,0,31);
    rotate(24,32,63);
    rotate(45,0,63);
}
void fifth(){//mayok
    rail_fence("31764528",8,0,63);
    caesar(10,0,31);
    rail_fence("2413",4,32,63);
    swap(0,63);
    trithemius(1,0,0,63);
    rotate(57,0,63);
}
void swap(int l,int r){ //good
    for(int i=l;i<=l+(r-l)/2;i++){
        keep[i]=c[i];
        c[i]=c[i+(r-l)/2+1];
        c[i+(r-l)/2+1]=keep[i];
    }
}
void rotate(int dig,int l,int r){
    dig%=(r-l+1);
    for(int i=l;i<l+dig;i++){
        keep[i+r-l+1-dig]=c[i];
    }
    for(int i=l+dig;i<=r;i++){
        keep[i-dig]=c[i];
    }
    for(int i=l;i<=r;i++){
        c[i]=keep[i];
    }
}
void rail_fence(char key[],int key_len,int l,int r){ //ok
    for(int first=l;first+key_len-1<=r;first+=key_len){
        for(int index=0;index<key_len;index++){
            keep[key[index]-'0'-1+first]=c[first+index];
        }
    }
    for(int i=l;i<r-(r+1)%key_len+1;i++){
        c[i]=keep[i];
    }
}
void caesar(int key,int l,int r){//ok
    key%=26;
    if(key<0)key+=26;
    for(int i=l;i<=r;i++){
        if('a'<=c[i]&&c[i]<='z'){
            if(c[i]+key>'z')c[i]+=key-26;
            else c[i]+=key;
        }
        else if('A'<=c[i]&&c[i]<='Z'){
            if(c[i]+key>'Z')c[i]+=key-26;
            else c[i]+=key;
        }
    }
}
void trithemius(int inde,int key,int l,int r){//ok
    if(inde==1){
        inde=key;
        for(int i=l;i<=r;i++){
            inde%=26;
            if(inde<0)inde+=26;
            if('a'<=c[i]&&c[i]<='z'){
                if(c[i]+inde>'z')c[i]+=inde-26;
                else c[i]+=inde;
                inde++;
            }else if('A'<=c[i]&&c[i]<='Z'){
                if(c[i]+inde>'Z')c[i]+=inde-26;
                else c[i]+=inde;
                inde++;
            }
        }
    }else if(inde==0){
        inde=key;
        for(int i=l;i<=r;i++){
            inde%=26;
            if(inde<0)inde+=26;
            if('a'<=c[i]&&c[i]<='z'){
                if(c[i]+inde>'z')c[i]=c[i]+inde-26;
                else c[i]+=inde;
                inde--;
            }else if('A'<=c[i]&&c[i]<='Z'){
                if(c[i]+inde>'Z')c[i]=c[i]+inde-26;
                else c[i]+=inde;
                inde--;
            }
        }
    }
}//increase inde=1; decrease inde=0
void vigenere(char key[],int key_len,int l,int r){
    int flag=0;
    for(int index=l;index<=r;index++){
        flag%=key_len;
        if('a'<=c[index] && c[index]<='z'){
            if(c[index]+key[flag]-'a'>'z')c[index]+=key[flag]-'a'-26;
            else c[index]+=key[flag]-'a';
            flag++;
        }else if('A'<=c[index] && c[index]<='Z'){
            if(c[index]+key[flag]-'a'>'Z')c[index]+=key[flag]-'a'-26;
            else c[index]+=key[flag]-'a';
            flag++;
        }
    }
}
