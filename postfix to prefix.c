#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

struct stringWithPointer{
   struct stringWithPointer* nextString;
   char* string;
};
struct stringWithPointer* topAddress;
bool isOperand(char c){
    switch(c){
        case '+': case'-': case'*': case'/': case'(': case')':
            return false;
        default:
            return true;
    }
}
bool isEmpty(){
    if(topAddress!=NULL) return false;
    else return true;
}
void push(struct stringWithPointer* inputAddress){
    if(!isEmpty()){
        inputAddress->nextString=topAddress; //make top address be the next one of input stringwithpointer
        topAddress=inputAddress; // change the top address into the input address
    }else{
        topAddress=inputAddress;//make input into top
    }
}//in: address of new calloced struct
char* pop(){
    if(isEmpty()){
        printf("\n-----\nError when doing pop\n-----\n");
        char* temp=NULL;
        return temp;
    }else{
//        printf("%s",topAddress->string);
        char* output = topAddress->string;
        struct stringWithPointer* temp=topAddress;
        if(topAddress->nextString!=NULL)
            topAddress = topAddress->nextString; //change topAddress into the next StringWithPointer's Address
        else topAddress = NULL;
        free(temp);
        return output;
    }
}// pop out the top string and free the space
char* makeString(char* element1, char* element2, char operator){
    int len1,len2, len;
    len1 = (int)strlen(element1); //length of element1
    len2 = (int)strlen(element2); //length of element2
    len = len1+len2+1; // length of the new string
    char* newStringAddress = calloc(len+1,sizeof(char));//+1 for'\0'
    for(int i=0;i<len1;i++){ //0~(len1-1), total:len1
        newStringAddress[i+1]=element1[i];
    }
    for(int i=0;i<len2;i++){ //len1~len1+(len2-1), total:len2
        newStringAddress[i+len1+1]=element2[i];
    }
    newStringAddress[0]=operator; //+1
    //last byte '\0' is init by calloc //https://stackoverflow.com/questions/51053061/relation-between-calloc-char-array-and-null-terminating-character
    return newStringAddress;
} // return the string connected
int main() {
    char input;
    while(scanf("%c",&input)&&input!='\n'){
        if(isOperand(input)){
//            printf("\n isOperand:%c\n",input);
            struct stringWithPointer* newChar = calloc(1,sizeof(struct stringWithPointer));
            char* newString = calloc(1,sizeof(char));
            *newString = input;
            newChar->string = newString;
            push(newChar);
        }else{
            struct stringWithPointer* newElement = calloc(1,sizeof(struct stringWithPointer));
            char* newString = makeString(pop(),pop(),input);
            newElement->string = newString;
            push(newElement);
        }
    }
    while(topAddress!=NULL){
        printf("%s",topAddress->string);
        topAddress = topAddress->nextString;
    }
    return 0;
}

