#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

struct node{
    char data;
    struct node *next; //top -> next -> next ... -> root
};

char symbol[]={'+','-','*','/'};
int priority[]={1 , 1 , 2 , 2 };

struct node * top=NULL;
bool isEmpty(){
    if(top == NULL) return true;
    else return false;
}
void push(char data){
    if(isEmpty()){
        struct node *new = calloc(sizeof(struct node),1);
        new->data=data;
        top = new;
    }else{
        struct node *new = calloc(sizeof(struct node),1);
        new->data=data;
        new->next=top;
        top = new;
    }
}
char pop(){
    if(isEmpty()){
        return 'n';
    }
    struct node *deleteNode = top;
    top = top->next;
    char c = deleteNode->data;
    free(deleteNode);
    return c;
}
int getPriority(char data){
    for(int i=0;i<=5;i++){
        if(data == symbol[i]){
            return priority[i];
        }
    }
}
/*
void printStack(){
    struct node *next=top;
    printf("\nprint stack:");
    if(isEmpty())return;
    while(next!=NULL){
        printf("%c",next->data);
        next=next->next;
    }
    printf("\n");
}//debug
*/
int main(){
    char input,p;
    int inputPriority=0;
    while(scanf("%c", &input) && input!='\n'){
//        printStack();
        switch(input){
            case '(':
                push(input);
                break;
            case ')':
                while((p=pop()) && (p!='(')){
                    printf("%c",p);
                }
                break;
            case '+': case '-': case '*': case '/':
                if(!isEmpty()) {
                    p = top->data;
                    inputPriority = getPriority(input);
                    while (inputPriority <= getPriority(p)) {
                        printf("%c", pop());
                        if(!isEmpty()){
                            p = top->data;
                        }else break;
                    }
                }
                push(input);
                break;
            default:
                printf("%c",input);
        }
    }
    while(!isEmpty()){
        printf("%c", pop());
    }
    return 0;
}

