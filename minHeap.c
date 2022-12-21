#include <stdio.h>
#include <stdlib.h>
#define INIT 10 //init space for heap
int nodeNum=0,spaceNum=INIT;
long *minHeap;
int minNode(int a,int b){
    if(b<=nodeNum){
        if(minHeap[a]<minHeap[b]) return a;
        else return b;
    }else{
        return a;
    }
}//b>a
void printHeap(){
    for(int i=1;i<=nodeNum;i++){
        printf("%d ",minHeap[i]);
    }
    printf("\n");
}//use for debugging
void checkSpace(){
     if(nodeNum>=spaceNum){
        minHeap= realloc(minHeap,(spaceNum+=30)*sizeof(long));
     }
}//realloc space for heap
void moveNode(long index){
    if(index>1 && minHeap[index]<minHeap[index/2]){
        while(index>1 && minHeap[index]<minHeap[index/2]){
            int tmp = minHeap[index];
            minHeap[index]=minHeap[index/2];
            minHeap[index/2]=tmp;
            index=index/2;
        }
    }else if(index*2<=nodeNum){ //there is child.
        // if nodeNum = index*2 , then there is only one child
        int smallChild = minNode(index*2,index*2+1);
        while(minHeap[index]>minHeap[smallChild]){ //if small one child is smaller than it, then change.
            int tmp = minHeap[index];
            minHeap[index]=minHeap[smallChild];
            minHeap[smallChild]=tmp;
            index=smallChild;
            // check if there is more child under it
            if(index*2<=nodeNum)
                smallChild = minNode(index*2,index*2+1);
            else break;
        }
    }else{ // there is no problem :D
        return;
    }
}//for change and remove
void swim(){
    int index = nodeNum;
    while(index>1 && minHeap[index]<minHeap[index/2]){
        int tmp = minHeap[index];
        minHeap[index]=minHeap[index/2];
        minHeap[index/2]=tmp;
        index=index/2;
    }
}//for insert
void insert(long value){
    checkSpace();
    minHeap[nodeNum]=value;
    swim();
}
void remov(){
    if(nodeNum<1){
        printf("empty\n");
        return;
    }
    printf("%ld\n",minHeap[1]);
    minHeap[1]=minHeap[nodeNum];
//    printf("\nnodenum=%d\n",nodeNum);
    nodeNum--;
//    printHeap();
    moveNode(1);
}
void change(long index, long newValue){
    if(nodeNum<index+1 || index<0){
        printf("out of range\n");
        return;
    }
    minHeap[index+1] = newValue;
    moveNode(index+1);
}

int main() {
    long num1, num2;//use for input numbers
    minHeap= calloc(INIT,sizeof(long));
    while(1){
        /* doing inputs*/
        char *s=calloc(10,sizeof(char));
        scanf("%s",s);
        switch(s[0]){
            case 'q':
                return 0;
            case 'i':
                nodeNum++;
                scanf("%ld",&num1);
                insert(num1); // insert value
                break;
            case 'r':
                remov();// remove by key
                break;
            case 'c':
                scanf("%ld",&num1);
                getchar();
                scanf("%ld",&num2);
                change(num1,num2); //change place num1's value into num2
                break;
        }
        free(s);
    }
}
