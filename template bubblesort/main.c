#include <stdio.h>
#define N 4
#define swap(x,y) {int tmp=x;x=y;y=tmp;}
int a[]={5,4,3,2};
int main() {
    for(int i=N-1;i>0;i--){
        for(int k=0;k<i;k++){
            if(a[k]>a[k+1]){
                swap(a[k],a[k+1])
            }
        }
    }
    for(int i=N-1;i>0;i--){
        for(int k=1;k<i+1;k++){
            if(a[k]<a[k-1]){
                swap(a[k],a[k-1])
            }
        }
    }
    for(int i=0;i<N;i++){
        printf("%d ",a[i]);
    }
    return 0;
}
