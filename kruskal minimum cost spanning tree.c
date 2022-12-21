#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
int *listOfRoots; // an array for the treenodes, record their root only
struct edgeNode{
    int weight;
    int a1,a2; //address number
};//use for edges minHeap

int find(int address){
    if(listOfRoots[address]==address){
        return address;
    }else{
        return listOfRoots[address]=find(listOfRoots[address]);
    }
}
bool isCycle(int a, int b){
    if(find(a)==find(b)) return true;
    else return false;
}
void unoon(int a, int b){
    listOfRoots[a]=b;
}//union
/* min heap included for edge*/
int edgeNum=0,spaceNum=30;
long long cost=0;
struct edgeNode * minHeap;
void printEdgeTree(){
    for(int i=1;i<=edgeNum;i++){
        printf("(%d,%d)%d->",minHeap[i].a1,minHeap[i].a2,minHeap[i].weight);
    }
    printf("\n");
}
void checkSpace(){
    if(edgeNum>=spaceNum){
        minHeap = realloc(minHeap,(spaceNum+=30)*sizeof(struct edgeNode));
    }
}
int minNode(int a, int b){
    if(b<=edgeNum){
        if(minHeap[a].weight<minHeap[b].weight) return a;
        else return b;
    }else return a;//only one child
}
void moveNode(int index){
    if(index*2>edgeNum) return;
    int smallChild = minNode(index*2,index*2+1);
    while(minHeap[index].weight>minHeap[smallChild].weight){
        struct edgeNode tmp = minHeap[index];
        minHeap[index]=minHeap[smallChild];
        minHeap[smallChild]=tmp;
        index=smallChild;
        if(index*2>edgeNum)break;
        smallChild = minNode(index*2,index*2+1);
    }
}
void swim(){
    int index = edgeNum;
    while(index>1 && minHeap[index].weight < minHeap[index/2].weight){
        struct edgeNode tmp = minHeap[index];
        minHeap[index]=minHeap[index/2];
        minHeap[index/2]=tmp;
        index=index/2;
    }
}
void insertEdge(struct edgeNode* newOnesAddress){
    checkSpace();
    minHeap[edgeNum]=*newOnesAddress;
    swim();
}
struct edgeNode* pop(){
    int w= minHeap[1].weight;
    int a1 = minHeap[1].a1;
    int a2 = minHeap[1].a2;
    struct edgeNode *ans = &minHeap[edgeNum];
    minHeap[1]=minHeap[edgeNum];
    ans->weight=w;
    ans->a1=a1;
    ans->a2=a2;
    edgeNum--;
    moveNode(1);
    return ans;
}

int main() {
    int e,v,s,t,c;
    scanf("%d %d",&v,&e);
    minHeap= calloc(30,sizeof(struct edgeNode));
    for(int i=0;i<e;i++){
        scanf("%d %d %d",&s,&t,&c);
        struct edgeNode* newEdge = calloc(1,sizeof(struct edgeNode) );
        newEdge->weight=c;
        newEdge->a1=s;
        newEdge->a2=t;
        edgeNum++;
        insertEdge(newEdge);
        free(newEdge);
    }
    //init sets
    listOfRoots = calloc(v,sizeof(int));
    for(int i=0;i<v;i++){
        listOfRoots[i]=i;
    }
    //pop out every edges to see is there a cycle
    for(int i=0;i<e;i++){
        struct edgeNode* current = pop();
        int a=current->a1,b=current->a2;
        if(!isCycle(a,b)){
            cost+=current->weight;
            unoon(find(a),find(b));
        }
    }
    printf("%lld",cost);
    return 0;
}
