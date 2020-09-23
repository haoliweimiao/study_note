# Pointer


## 函数指针数组
``` c
#include <stdio.h>
 
int add(int a,int b){
    return a+b;
}
int sub(int a,int b){
    return a-b;
}
int mul(int a,int b){
    return a*b;
}
int div(int a,int b){
    return a/b;
}
void make_menu(){
    printf("****************************\n");
    printf("请选择菜单：\n");
    printf("1：加 2：减 3：乘 4：除 0：退出 \n");
}
/*定义函数指针数组变量
(int,int) 对应于函数指针数组 指向的函数列表
*/
int (*fun_array[4]) (int,int) = {add, sub, mul, div};
 
int main(){
    int i,j;
    int cmd;
    while(1){
        make_menu();
        scanf("%d",&cmd);
        if(cmd==0){
            break;
        }
        if(cmd>=1&&cmd<=4){
            printf("请输入2个数字:");
            scanf("%d%d",&i,&j);
            //通过函数指针数组去调用对应的函数
            int result = fun_array[cmd-1](i,j); //等同于 int result = (*fun_array[cmd-1])(i,j);
            //通过函数指针变量来调用对应的函数
            //int (*p)(int,int) = fun_array[cmd-1];
            //int  result = p(i,j);
            printf("result:%d\n",result);
        }
    }
     
    return 0;
}
```