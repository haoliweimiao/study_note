# C语言中函数参数传递的三种方式

C语言中函数参数传递的三种方式

（1）传值，就是把你的变量的值传递给函数的形式参数，实际就是用变量的值来新生成一个形式参数，因而在函数里对形参的改变不会影响到函数外的变量的值。
（2）传址，就是传变量的地址赋给函数里形式参数的指针，使指针指向真实的变量的地址，因为对指针所指地址的内容的改变能反映到函数外，也就是能改变函数外的变量的值。
（3）传引用，实际是通过指针来实现的，能达到使用的效果如传址，可是使用方式如传值。
说几点建议：如果传值的话，会生成新的对象，花费时间和空间，而在退出函数的时候，又会销毁该对象，花费时间和空间。
因而如果int，char等固有类型，而是你自己定义的类或结构等，都建议传指针或引用，因为他们不会创建新的对象。


## 例1
``` c
#include<stdio.h>
void change(int*a, int&b, int c)
{
      c=*a;
      b=30;
      *a=20;
}

int main ( )
{
      int a=10, b=20, c=30;
      change(&a,b,c);
      printf(“%d,%d,%d,”,a,b,c)；
      return 0；
 }
// 结果：20  30  30
```


解析：
该题考察函数传参问题。
1，指针传参 -> 将变量的地址直接传入函数，函数中可以对其值进行修改。
2，引用传参 -> 将变量的引用传入函数，效果和指针相同，同样函数中可以对其值进行修改。
3，值传参   -> 在传参过程中，首先将c的值复制给函数c变量，然后在函数中修改的即是函数的c变量，然后函数返回时，系统自动释放变量c。而对main函数的c没有影响。


## 例2
``` c
#include<stdio.h>  
void myswap(int x, int y)  
{  
    int t;  
    t=x;  
    x=y;  
    y=t;  
}  
int main()  
{  
    int a, b;  
    printf("请输入待交换的两个整数：");  
    scanf("%d %d", &a, &b);  
    myswap(a,b);  //作为对比，直接交换两个整数，显然不行  
    printf("调用交换函数后的结果是：%d 和 %d\n", a, b);  
    return 0;  
}  
 
 
#include<stdio.h>  
void myswap(int *p1, int *p2)  
{  
    int  t;  
    t=*p1;  
    *p1=*p2;  
    *p2=t;  
}  
int main()  
{  
    int a, b;  
    printf("请输入待交换的两个整数：");  
    scanf("%d %d", &a, &b);  
    myswap(&a,&b);  //交换两个整数的地址  
    printf("调用交换函数后的结果是：%d 和 %d\n", a, b);  
    return 0;  
}  
 
#include<stdio.h>  
void myswap(int &x, int &y)  
{  
    int t;  
    t=x;  
    x=y;  
    y=t;  
}  
  
int main()  
{  
    int a, b;  
    printf("请输入待交换的两个整数：");  
    scanf("%d %d", &a, &b);  
    myswap(a,b);  //直接以变量a和b作为实参交换  
    printf("调用交换函数后的结果是：%d 和 %d\n", a, b);  
    return 0;  
}
// 第一个的运行结果：输入2 3，输出2 3 
// 第二个的运行结果：输入2 3，输出3 2
// 第三个的运行结果：输入2 3，输出3 2

// 解析：
// 在第一个程序中，传值不成功的原因是指在形参上改变了数值，没有在实参上改变数值。
// 在第二个程序中，传地址成功的原因利用指针改变了原来的地址，所以实参就交换了。
// 在第三个程序中，引用是直接改变两个实参变量a，b的值，所以就交换了。
```


下文会通过例子详细说明关于值传递，指针传递，引用传递 

1. 值传递：
形参是实参的拷贝，改变形参的值并不会影响外部实参的值。从被调用函数的角度来说，值传递是单向的（实参->形参），参数的值只能传入，不能传出。当函数内部需要修改参数，并且不希望这个改变影响调用者时，采用值传递。

2. 指针传递：
形参为指向实参地址的指针，当对形参的指向操作时，就相当于对实参本身进行的操作

3. 引用传递：
形参相当于是实参的“别名”，对形参的操作其实就是对实参的操作，在引用传递过程中，被调函数的形式参数虽然也作为局部变量在栈中开辟了内存空间，但是这时存放的是由主调函数放进来的实参变量的地址。被调函数对形参的任何操作都被处理成间接寻址，即通过栈中存放的地址访问主调函数中的实参变量。正因为如此，被调函数对形参做的任何操作都影响了主调函数中的实参变量。