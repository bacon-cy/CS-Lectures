#include<stdio.h>

int *poly(int*, int*, int*);

int main() {
    int a, b, c;
    int *A, *B, *C;
    scanf("%d %d %d", &a, &b, &c);
    A = &a;
    B = &b;
    C = &c;
    if (a < 0) A = NULL;
    if (b < 0) B = NULL;
    if (c < 0) C = NULL;

    int *result = poly(A, B, C);
    printf("%d", *result);
    return 0;
}
int *poly(int *A, int *B, int *C) {
    int k =0;
    static int ans=0;
    int *p=&ans;
    if(!A)A=&k;
    if(!B)B=&k;
    if(!C)C=&k;
    ans=*A * *A * *A + *B * *B + *C;
    return p;
}