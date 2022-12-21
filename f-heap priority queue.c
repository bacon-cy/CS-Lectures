#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
struct node{
    struct node* left; //sibling
    struct node* right; //sibling
    struct node* child; //one of its child
    struct node* parent;
    int degree;
    int key; //to compare
    int value;
    bool flag; //for f-heap
    struct node* next; // for hash table
    struct node* front; // for hash table, im so lazy QQ
};
struct node* topMin;
int topLevelDegree;
struct node* CheckTable[20];
struct node* HashTable[(int)1e5];
// for debug :D
void printSibling(struct node* a){
    printf("%d,%d ->",a->key,a->value);
//    printf("l:%d r:%d\n",a->left->key,a->right->key);
    if(a->right!=a){
        struct node* tmp = a->right;
        while(tmp!=a) {
            printf("%d,%d ->", tmp->key, tmp->value);
            tmp = tmp->right;
//            printf("l:%d r:%d\n",tmp->left->key,tmp->right->key);
        }
    }
    printf("circle end \n");
}
// functions
void checkTopMin(struct node* a){
    int min=(int)2e31;
    for(int i=0;i<topLevelDegree;i++){
        //check topMin
        if(a->key < min){
            min = a->key;
            topMin = a;
        }
        a = a->left;
    }
}
void addingHash(struct node* theNode){
    int xHash = theNode->key%(int)1e5;
    if(xHash<0) xHash *=-1;
    struct node* tmp = HashTable[xHash];
    if(tmp==NULL){
        HashTable[xHash]=theNode;
    }else{
        while(tmp->next!=NULL){
            tmp = tmp->next;
        }
        theNode->front=tmp;
        tmp->next=theNode;
    }
}
void deleteHash(struct node* theNode){
    if(theNode->front!=NULL){
        theNode->front->next=theNode->next;
        if(theNode->next!=NULL)theNode->next->front=theNode->front;
    }else{
        int xHash=theNode->key%(int)1e5;
        if(xHash<0)xHash*=-1;
        if(theNode->next!=NULL)HashTable[xHash]=theNode->next;
        else HashTable[xHash]=NULL;
    }
}
struct node* findNode(int x, int value){
    int xHash = x%(int)1e5;
    if(xHash<0) xHash*=-1;
    struct node* tmp = HashTable[xHash];
    while(tmp!=NULL && tmp->value!=value){
        tmp=tmp->next;
    }
    return tmp;
}

void insert(struct node* new){ //key, value, degree, child and next is the child before it been cut(or when it is init in main)
    topLevelDegree++;
    new->flag=0;
    new->parent=NULL;
    //left and right, insert at the left of topMin
    if(topMin==NULL){
        topMin=new;
        new->left = new;
        new->right = new;
        return;
    }
    struct node* tmp = topMin->left;
    tmp->right = new;
    new->left = tmp;
    new->right = topMin;
    topMin->left = new;

    //check topMin
    if(new->key < topMin->key){
        topMin = new;
    }
}//insert tree, init input node is in main! //need to topLevelDegree++

void cutOff(struct node* theNode){
    struct node* theParent = theNode->parent;
    //check parent
    theParent->degree--;
    if(theParent->child==theNode){
        theParent->child=theNode->left;
    }
    if(theParent->flag!=true){
        theParent->flag=true;
    }else{
        cutOff(theParent);
    }
    //check sibling
    theNode->left->right=theNode->right;
    theNode->right->left=theNode->left;
    // move theNode into Root list
    insert(theNode);
}
void checkCutOff(struct node* theNode){
    if(theNode->parent!=NULL && theNode->key < theNode->parent->key){
        cutOff(theNode);
    }else if(theNode->parent==NULL){
        if(theNode->key<topMin->key) topMin = theNode;
    }
}
void decrease(int x, int value, int y){
    struct node* theNode = findNode(x,value);
    deleteHash(theNode);
    theNode->key-=y;
    addingHash(theNode);
    checkCutOff(theNode);
}

void delete(int x, int value){
    struct node* theNode = findNode(x,value);
    if(theNode==NULL)return;
    deleteHash(theNode);
    //parent
    if(theNode->parent!=NULL){
        theNode->parent->degree--;
        if(theNode->parent->flag==false)theNode->parent->flag=true;
        else cutOff(theNode->parent);
    }else{
        topLevelDegree--;
    }
    //sibling
    theNode->left->right = theNode->right;
    theNode->right->left = theNode->left;
    //insert all child into top level
    struct node* tmp = theNode->child;
    struct node* tmpLeft;
    if(theNode==topMin){
        if(topMin->left!=topMin){
            topMin=topMin->left;//we need to change the topMin before insert, or it will insert wrong place
        }
        else if(topMin->child!=NULL) topMin=topMin->child;
        else topMin =NULL;
    }
    for(int i=0;i<theNode->degree;i++){
        tmpLeft = tmp->left;
        insert(tmp);
        tmp = tmpLeft;
    }
    //check topMin after insert child
    if(theNode==topMin){
        if(theNode->left==theNode && theNode->child!=NULL) topMin=theNode->child;
        else if(theNode->left!=theNode) topMin=theNode->left;
        else topMin = NULL;
        if(topMin!=NULL) checkTopMin(topMin);
    }
    free(theNode);
}
void clearValue(struct node* t){
    t->left=t;
    t->right=t;
    t->parent=NULL;
    t->flag=false;
    //next, front, child, key, value, degree no change
}
void meld(struct node *tree1,struct node *tree2){
    CheckTable[tree1->degree]=NULL;
    if(tree1->key>tree2->key){
        struct node* tmp = tree1; tree1 = tree2; tree2 = tmp;
    }
    tree2->parent = tree1;
    tree1->degree++;
    //add tree2 into the sibling circular
    if(tree1->child!=NULL){
        tree2->left = tree1->child;
        tree2->right = tree1->child->right;
        tree1->child->right->left=tree2;
        tree1->child->right=tree2;
    }else{
        tree1->child=tree2;
    }
    //put it into table
    if(CheckTable[tree1->degree]==NULL)CheckTable[tree1->degree]=tree1;
    else{
        meld(tree1,CheckTable[tree1->degree]);
    }
}
void rebuildHeap(){
    topMin=NULL;
    topLevelDegree=0;
    for(int i=0;i<20;i++){
        if(CheckTable[i]!=NULL){
            insert(CheckTable[i]);
        }
    }
}
void extract(){
    if(topMin==NULL)return;
    printf("(%d)%d\n",topMin->key,topMin->value);
    delete(topMin->key,topMin->value);
    for(int i=0;i<20;i++) CheckTable[i]=NULL;
    struct node *tmp = topMin;
    struct node *next;
    for(int i=0;i<topLevelDegree;i++){
        next = tmp->left;
        clearValue(tmp);
        if(CheckTable[tmp->degree]==NULL)CheckTable[tmp->degree]=tmp;
        else {
            meld(tmp,CheckTable[tmp->degree]);
        }
        tmp = next;
    }
    rebuildHeap();
}
int main() {
    while(true){
        int a,b,c;
        char *s=calloc(10,sizeof(char));
        scanf("%s",s);
        switch(s[0]){
            case 'q':
                return 0;
            case 'i':
                scanf("%d%d",&a,&b);
                {struct node* new = calloc(1,sizeof(struct node));
                    //flag=false,degree=0,parent=NULL,child=NULL init by calloc
                    new->key=a;
                    new->value=b;
                    //next : hash table
                    int xHash = a%(int)1e5;
                    if(xHash<0) xHash*=-1;
                    struct node* tmp = HashTable[xHash];
                    if(tmp==NULL){
                        HashTable[xHash]=new;
                    }else{
                        while(tmp->next!=NULL){
                            tmp = tmp->next;
                        }
                        new->front=tmp;
                        tmp->next=new;
                    }
                    insert(new);}
                break;
            case 'd':
                if(s[2]=='c'){
                    scanf("%d%d%d",&a,&b,&c);
                    decrease(a,b,c);
                }else{
                    scanf("%d%d",&a,&b);
                    delete(a,b);
                }
                break;
            case 'e':
                extract();
//                printf("extracted\n");
                break;
            default:
                printf("Error!\n");
        }
    }
}