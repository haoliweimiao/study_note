---
title:单例模式 Singleton
---

# 单例模式 Singleton

## 意图
+ 保证一个类仅有一个实例，并提供一个访问它的全局节点。

## 动机
+ 在软件系统中，经常有这样一些特殊的类，必须保证它们在系统中只存在一个实例，才能保证它们的逻辑正确性、以及良好的效率。

  + 对于一些类来说，只有一个实例是很重要的。虽然系统中可以有很多打印机，但却只应有一个打印假脱机（printer spooler），只应该有一个文件系统和一个窗口管理器。一个数字滤波器只能有一个A/D转换器。一个会计系统只能专用于一个公司。

  + 面对对象很好地解决了“抽象”的问题，但是必不可免的要付出一些代价。对于通常情况来讲，面对对象的成本都可以忽略不计。但是某些情况，面对对象所带来的成本必须谨慎对待。

~~~ java
public class Singleton {
    private static Singleton mSingleton;

    private Singleton() {
    }

    /**
     * 非线程安全
     */
    public Singleton getInstance() {
        if (mSingleton == null) {
            mSingleton = new Singleton();
        }
        return mSingleton;
    }

    /**
     * 线程安全，加锁代价过高
     */
    public Singleton getLockInstance() {
        synchronized (Singleton.class) {
            if (mSingleton == null) {
                mSingleton = new Singleton();
            }
        }

        return mSingleton;
    }

    /**
     * 双检查锁，但是由于内存读写reorder不安全
     */
    public Singleton getDoubleLockInstance() {
        if (mSingleton == null) {
            synchronized (Singleton.class) {
                mSingleton = new Singleton();
                //正常执行步骤 1.分配内存 2.调用构造器 3.地址赋值给mSingleton
                //CPU reorder后可能不是这个步骤，导致mSingleton被分配了内存，但是还是空值 例如 1.分配内存 2.地址赋值给mSingleton 3.调用构造器
            }
        }

        return mSingleton;
    }


    /**
     * 使用 static 字段的单例实现
     */
    private static Singleton instance = new Singleton();
    public Singleton getSingleton(){
        return instance;
    }

}
~~~