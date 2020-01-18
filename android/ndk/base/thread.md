# 线程
作为多线程环境的一部分，虚拟机支持运行的原生代码。在开发原生构件时要记住jni技术的一些约束：
* 只在原生方法执行期间及在执行原生方法的线程环境下局部引用是有效的，局部引用不能在多线程间共享，只有全局引用可以被多个线程共享。
* 被传递给每个原生方法的JNIEvn接口指针在与方法调用相关的线程中是有效的，它不能被其他线程缓存或使用。

[同步](#同步)
[原生线程](#原生线程)

## 同步
同步是多线程程序设计最重要的特征。与Java的同步类似，jni的监视器允许原生代码利用java对象同步，
虚拟机保证存取监视器的线程能安全执行，而其他线程等待监视器对象变成可用状态。

~~~ java
//java同步代码块
synchronized(obj){
    //同步线程安全代码块
}
~~~

在原生代码中，相同级别同步可以用jni的监视方法实现的
~~~ c
//java同步代码块的原生等价
if(JNI_OK == (*env)->MonitorEnter(env,obj)){
    //错误处理
}
//同步线程安全代码块
if(JNI_OK == (*env)->MonitorExit(env,obj)){
    //错误处理
}
~~~
#对MonitorEnter函数的调用应该与对MonitorExit的调用匹配，从而避免代码出现死锁。


## 原生线程
为了执行特定任务，这些原生构件可以并行使用原生线程。因为虚拟机不知道原生线程，因此它们不能与java构件
直接通信。为了与应用的依然活跃部分交互，原生线程应该先附着在虚拟机上。
jni通过JavaVM接口指针提供了AttachCurrentThread函数以便于让原生代码将原生线程附着在虚拟机上，
javaVM接口指针应该尽早被缓存，否则的话它不能被附着。

~~~ c
//当前线程与虚拟机附着和分离
JavaVM* cachedJvm;
...
JNIEnv* env;
...
//将当前线程附着到虚拟机
(*cachedJvm)->AttachCurrentThread(cachedJvm,&env,NULL);
//可以用JNIEnv接口实现线程与Java应用程序的通信
//将当前线程与虚拟机分离
(*cachedJvm)->DetachCurrentThread(cachedJvm);
~~~

对AttachCurrentThread函数的调用允许应用程序获得对当前线程有效的JNIEnv接口指针。将一个已经
附着的原生线程再次附着不会有任何副作用。当原生线程完成时，可以用DetachCurrentThread函数将原生
线程与虚拟机分离。