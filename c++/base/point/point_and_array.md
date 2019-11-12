# Point And Array

+ [指针和数组](#指针和数组)
+ [指针和二维数组](#指针和二维数组)
+ [指向指针的指针]](#指向指针的指针)

## 指针和数组
+ 数组的名字是一个指针常量，指向数组的起始位置(数组名称是指向第一个元素的指针)
    + T a[N]; 
        + a的类型是 T *
        + 可以用a给一个T * 类型的指针赋值
        + a是编译时其值就确定了的常量，不能对a进行赋值

+ 作为函数形参时， T *p 和 T p[] 等价
    + void Func(int *p){}
    + void Func(int p[]){}


~~~ c++
void point_base_demo_two()
{
    using namespace std;
    int a[200];
    int *p;
    // p指向数组a起始地址，亦即p指向了a[0]
    p = a;
    // 使得a[0] = 10
    *p = 10;
    // 使得a[1] = 20
    *(p + 1) = 20;
    // p[i] 和 *(p+i) 是等效的，使得a[0] = 30
    p[0] = 30;
    // 使得 a[4] = 40
    p[4] = 40;
    for (int i = 0; i < 10; ++i)
    {
        // 对数组a的10个元素进行赋值
        *(p + i) = i;
    }

    // p 指向 a[1]
    ++p;
    // 输出1 p[0]等效于*p，p[0]即是a[1]
    cout << p[0] << endl;
    // p指向a[6]
    p = a + 6;
    // 输出6
    cout << *p << endl;
    // p指向a[0]
    p = a;
    // 输出0
    cout << *p << endl;
}
~~~

## 指针和二维数组

如果定义二维数组：
    T a[M][N];

+ a[i](i是整数)是一个一维数组
+ a[i]的类型是 T *
+ sizeof(a[i]) = sizeof(T) * N
+ a[i]指向的地址：数组a的起始位置 + i*N*sizeof(T)

## 指向指针的指针
定义
T **p;
p是指向指针的指针，p指向的地方应该存放着一个类型为 T * 的指针
* p 的类型是 T *

~~~ c++
void func(){
    int **pp;
    int *p;
    int n = 1234;
    p = &n;//p指向n,*p = 1234
    pp = &p;//pp指向p,*PP = p
    cout << *(*pp) <<endl;
}
~~~