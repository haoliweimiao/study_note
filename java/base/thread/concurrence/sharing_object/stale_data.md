---
title:过期数据
---
# 过期数据

> 没有恰当同步的程序，它能够引起意外的后果：过期数据
(NoVisibility demonstrated one of the ways that insufficiently synchronized programs can cause surprising results: stale data.)

> 一个线程可能会得到一个变量最新的值，但是也可能得到另一个变量先前写入的过期值。

***
### 非线程安全的可变整数访问器

    @NotThreadSafe
    public class MutableInteger{
        private int value;

        public int get(){
            return value;
        }

        public void set(int value){
            this.value = value;
        }
    }


### 线程安全的可变整数访问器

    @ThreadSafe
    public class SynchronizedInteger{
        @GuardedBy("this") private int value;
    
        public synchronized int get(){
            return value;
        }
    
        public synchronized void set(int value){
            this.value = value;
        }
    }