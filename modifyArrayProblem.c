#include <stdio.h>
#include <assert.h>
#define SIZE 201

void modify_array(int *);

int main(void) {
    int arr[SIZE];
    for (int i = 0; i < SIZE; i++) {
        scanf("%d", &arr[i]);
    }
    modify_array(&arr[SIZE/2]);
    for (int i = 1; i < SIZE; i++) {
        printf("%d ", arr[i]);
    }
    return 0;
}
void modify_array(int *p){
    p-=SIZE/2;
    if(*p == 1){
        p++;
        for(int index=0;index<SIZE-1;p+=2,index+=2){
            *p += 10;
        }
    }else if(*p == 2){
        p++;
        for(int index=0;index<SIZE-1;p+=3,index+=3){
            *p *= 8;
        }
    }else if(*p == 3){
        p++;
        for(int index=0;index<SIZE-1;p+=5,index+=5){
            *p -= 2;
        }
    }else assert(0);
}