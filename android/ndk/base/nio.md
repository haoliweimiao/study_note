# NIO操作

原生I/O(NIO)在缓冲管理区、大规模网络和文件I/O及字符集支持方面的性能有所改进。JNI提供了在原生代码中
使用NIO的函数。与数组操作比较，NIO缓冲区的数据传送性能较好，更适合在原生代码和Java应用程序之间传送
大量数据。


+ [创建直接字节缓冲区](#创建直接字节缓冲区)
+ [直接字节缓冲区获取](#直接字节缓冲区获取)

## 创建直接字节缓冲区
原生代码可以创建Java应用程序使用的直接字节缓冲区，该过程是以提供一个原生C字节数组为基础。

~~~ c
//基于给定的c字节数组创建字节缓冲区
unsigned char * buffer = (unsigned char *) malloc(1024);
//...
jobject directBuffer;
directBuffer = (*env)->NewDirectByteBuffer(env, buffer, 1024);
~~~

注意：原生方法中的内存分配超出了虚拟机的管理范围，且不能用虚拟机的垃圾回收器回收原生方法中的内存。
原生函数应该通过释放未使用的内存分配以避免内存泄漏来正确管理内存。

## 直接字节缓冲区获取
Java应用程序中也可以创建直接字节缓冲区，在原生代码中调用GetDirectBufferAddress函数可以
获得原生字节数组的内存地址。如下；

~~~ c
//通过Java字节缓冲区获取原生字节数组
unsigned char* buffer;
buffer = (unsigned char*) (*env)->GetDirectBufferAddress(env, directBuffer);
~~~