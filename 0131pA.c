#include <stdio.h>
#include <stdlib.h>

// Enumrations
enum orb_type {
    O_UNSET = -1,
    O_RED = 17, O_BLUE = 1, O_GREEN = 6, O_YELLOW = 24,
    O_FLOAT = 5, O_CLONE = 2, O_EXPAND = 4, O_DYSON = 3
};

// Structure Declairation
struct node_t {
    struct node_t *next;
    enum orb_type orb;
};

struct head_t {
    struct node_t *head;
    int count;
    enum orb_type orb;
};

// Function Prototypes
struct head_t *alloc_list_heads(int size);              // Allocate memory for array of list heads
void init_head_arr(struct head_t *arr, int size);       // Initialize array of linked-lists
void print_magic_circle(struct head_t *arr, int size);  // Print the structure of magic circle
void insert_next(struct head_t *arr, int size, const char c);  // Insert the next orb onto the magic circle

// Main Function
int main() {
    int size;       // Size of center ring
    int count;      // Number of stem orbs will be put;

    // Allocate ring
    scanf("%d%d%*c", &size, &count);
    struct head_t *center_ring = alloc_list_heads(size);

    // Read and set ring
    init_head_arr(center_ring, size);

    // Place the orbs
    for (int i = 0; i < count; ++i) {
        insert_next(center_ring, size, getchar());
    }

    // Print magic circle configuration
    print_magic_circle(center_ring, size);
    return 0;
}

// Function Definitions
void print_magic_circle(struct head_t *arr, int size) {
    if (!arr) {
        return;
    }

    for (int i = 0; i < size; ++i) {
        // Print center orb
        printf("CO_%c", arr[i].orb + 'A');

        // Print stem
        struct node_t *cur = arr[i].head;
        while (cur) {
            printf(" -> %c", 'A' + cur->orb);
            cur = cur->next;
        }
        if (i + 1 < size) putchar('\n');
    }
    return;
}
#include <stdbool.h>
bool have_f[100];
int have_c[100],cSum;
int orb_num;
struct head_t *alloc_list_heads(int size){
    struct head_t *p =calloc(size,sizeof(struct head_t));
    return p;
}
void init_head_arr(struct head_t *arr, int size){
    for(int i=0;i<size;i++){
        arr[i].orb=getchar()-'A';
    }
    getchar();
}
void insert_next(struct head_t *arr, int size, const char c){
    orb_num++;
    const int stem = (orb_num-1)%size; //ok
    if(have_f[stem] && c-'A'==O_FLOAT)return; // float break as soon as put
    arr[stem].count++;
    struct node_t *tmp;
    if(orb_num<=size){
        arr[stem].head= calloc(1,sizeof(struct node_t));
        arr[stem].head->orb= c-'A';
    }else{
        tmp=arr[stem].head;
        if(!have_f[stem]){
            while(tmp->next!=NULL)tmp=tmp->next;
            tmp->next = calloc(1,sizeof(struct node_t));
            tmp->next->orb=c-'A';
        }else{
            struct node_t *a = calloc(1,sizeof(struct node_t));
            a->orb=c-'A';
            a->next=arr[stem].head;
            arr[stem].head=a;
        }
    }
    int plus=0,copyStem;
    struct node_t *t;
    struct node_t *an;
    switch(c-'A'){
        case O_FLOAT:
            have_f[stem]=1;
            break;
        case O_CLONE:
            have_c[stem]++;
            cSum++;
            copyStem = (size-(cSum%size)+stem)%size;
            if(copyStem==stem)break;
            if(arr[copyStem].count>0){
                t=arr[copyStem].head;
                an=arr[copyStem].head->next;
                for(int i=1;i<arr[copyStem].count;i++){
                    free(t);
                    t=an;
                    an=an->next;
                }
            }
            arr[copyStem].count=arr[stem].count;
            cSum+=have_c[stem]-have_c[copyStem];
            have_c[copyStem]=have_c[stem];
            have_f[copyStem]=have_f[stem];
            arr[copyStem].head = calloc(1,sizeof(struct node_t));
            t = arr[copyStem].head;
            an = arr[stem].head;
            t->orb=an->orb;
            an=an->next;
            for(int i=1;i<arr[stem].count;i++){
                t->next = calloc(1,sizeof(struct node_t));
                t=t->next;
                t->orb=an->orb;
                an=an->next;
            }
            break;
        case O_DYSON:
            for(int i=0;i<size;i++){
                if(i==stem)continue;
                t=arr[i].head;
                while(t!=NULL && t->orb==arr[stem].orb){
                    arr[i].count--;
                    plus++;
                    arr[i].head=t->next;
                    t=t->next;
                }
                for(int k=0;k<arr[i].count-1;k++){
                    if(t->next->orb==arr[stem].orb){
                        arr[i].count--;
                        plus++;
                        t->next=t->next->next;
                    }
                    t=t->next;
                }
            }
            for(int i=0;i<plus;i++){
                arr[stem].count++;
                if(orb_num<=size){
                    arr[stem].head= calloc(1,sizeof(struct node_t));
                    arr[stem].head->orb= arr[stem].orb;
                }else{
                    tmp=arr[stem].head;
                    if(!have_f[stem]){
                        while(tmp->next!=NULL)tmp=tmp->next;
                        tmp->next = calloc(1,sizeof(struct node_t));
                        tmp->next->orb=arr[stem].orb;
                    }else{
                        struct node_t *a = calloc(1,sizeof(struct node_t));
                        a->orb=arr[stem].orb;
                        a->next=arr[stem].head;
                        arr[stem].head=a;
                    }
                }
            }
            break;
        case O_EXPAND:
            for(int i=0;i<size;i++){
                insert_next(arr,size,arr[stem].orb+'A');
            }
            break;
    }
    return;
}