# Void Point

[概念](#概念)
[memset](#memset)
[memcpy](#memcpy)


## 概念
+ void * p;
+ 可以用任何类型的指针对void 指针进行赋值或者初始化:
    + ~~~ c++
      double d = 1.54;
      void * p = &d;
      void * p1;
      p1 = &d;
      ~~~
      ~~~

+ 因 sizeof(void) 没有定义所以对 void * 类型的指针p,
  *p 无定义
  ++p, --p , p+=n, p+n , p-n 均无定义

## memset
头文件cstring中声明：
void * memset(void * dest,int ch , int n);

将从dest开始的n个字节，都设置成ch，返回值是dest。ch只有最低的字节起作用。

~~~ c++
//将szName的前10个字符，都设置成'a'
char szName[200]="";
memset(szName,'a',10);
~~~

## memcpy
头文件cstring中声明：
void * memset(void * dest,void * src , int n);
将地址src开始的n个字节，拷贝到地址dest。返回值是dest。

~~~ c++
//将数组a1的内容拷贝到数组a2中去，结果是a2[0] = a1[0],.....a2[9] = a1[9]:
int a1[10];
int a2[10];
memcpy(a2, a1, 10*sizeof(int));
~~~

~~~ c++
void * my_memset(void * dest,void * src , int n){
    //src 与 dest有重叠情况会出错
    char * pDest = (char *)dest;
    char * pSrc = (char *)src;
    for(int i = 0;i < n; i++){
        *(pDest + i) = *(pSrc + i);
    }
    return dest;
}
~~~