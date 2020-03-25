# error

## 如何解决warning: incompatible implicit declaration of built-in function 'malloc'

在代码中包含：
~~~ c
int *x = malloc(sizeof(int)); 
~~~

得到gcc编译错误:
~~~ shell
***: warning: implicit declaration of function ‘malloc’
***: warning: incompatible implicit declaration of built-in function ‘malloc’
~~~

解决办法
加入下面的这一行
#include <stdlib.h>