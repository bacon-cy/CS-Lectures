#include <stdio.h>
#include <string.h>
#define MAX_LEN_P 100000
#define MAX_LEN_S 100

void strins(char *P, char *s, char *t);

int main() {
    char P[MAX_LEN_P + 1], s[MAX_LEN_S + 2], t[MAX_LEN_S + 2];
    size_t P_len = 0;
    for (char buf[MAX_LEN_P + 1] = ""; strcmp(buf, "---\n"); fgets(buf, MAX_LEN_P + 1, stdin)) {
        size_t len = strlen(buf);
        if (len + P_len > MAX_LEN_P) {
            fprintf(stderr, "The length of the paragraph exceeds %d\n", MAX_LEN_P);
            return 1;
        }
        strncat(P, buf, len+1);
        P_len += len;
    }
    fgets(s, MAX_LEN_S + 2, stdin);
    s[strlen(s) - 1] = '\0';  // remove newline
    fgets(t, MAX_LEN_S + 2, stdin);
    t[strlen(t) - 1] = '\0';  // remove newline
    strins(P, s, t);
    printf("%s", P);
    return 0;
}
#include <stdbool.h>
void insertS(char *first, const char *t,char *P){
    int P_len=(int)strlen(P);
    int t_len=(int)strlen(t);
    char *yee=first;
    char keep[MAX_LEN_P];
    for(int i=0;i<P_len-(first-P);i++){
        keep[i]=*(first+i);
    }
    for(int i=0;i<t_len;i++){
        *yee++=*(t+i);
    }
    for(int i=0;i<P_len-(first-P);i++,yee++){
        *yee=keep[i];
    }
}
void strins(char *P, char *s, char *t){
    int p_len = (int)strlen(P);
    int t_len = (int)strlen(t);
    int s_len = (int)strlen(s);
    int pluss = 0;
    bool insertHere[MAX_LEN_P]={0};
    for(char *p=P;p<P+p_len-s_len;p++){
        for(int i=0;i<s_len;i++){
            if(*(p+i)!=*(s+i))break;
            if(i==s_len-1)insertHere[(p-P)+i+1]=1;
        }
    }
    for(int i=0;i<p_len;i++){
        if(insertHere[i]==1){
            insertS(P+i+pluss,t,P);
            pluss+=t_len;
        }
    }
}
