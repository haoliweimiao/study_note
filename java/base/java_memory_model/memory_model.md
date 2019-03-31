---
title:Java存储模型
---
# Java存储模型
## The Java Memory Model

### What is a Memory Model, and Why would I Want One?

Suppose one thread assigns a value to aVariable:

aVariable = 3;


    A memory model addresses the question "Under what conditions does a thread that reads aVariable see the value 3?" This may sound like a dumb question, but in the absence of synchronization, there are a number of reasons a thread might not immediately ‐ or ever ‐ see the results of an operation in another thread. (一个存储模型解释这个问题：“在什么条件下一个线程可以读取到aVariable的值为3?”。这听起来像是一个愚蠢的问题，但在缺乏同步的情况下，有很多原因导致一个线程可能不会立即(或永远)看到另一个线程中的操作结果。)
    Compilers may generate instructions in a different order than the "obvious" one suggested by the source code, or store variables in registers instead of in memory; processors may execute instructions in parallel or out of order; caches may vary the order in which writes to variables are committed to main memory; and values stored in processor‐local caches may not be visible to other processors. (编译器可能生成不同顺序的指令而不是源码“明显的”建议生成的顺序，而且编译器将存储变量在寄存器中而不是内存里；处理器可以并行执行指令，也可以无序执行指令；缓存可以改变将变量写入主内存的顺序;存储在本处理器的值对其它处理器不可见。)
    These factors can prevent a thread from seeing the most up‐to‐date value for a variable and can cause memory actions in other threads to appear to happen out of order ‐ if you don't use adequate synchronization.(
    这些因素可以防止线程看到变量的最新值，如果没有使用足够的同步，还可能导致其他线程中的内存操作出现顺序错误。)

    In a single‐threaded environment, all these tricks played on our program by the environment are hidden from us and have no effect other than to speed up execution.(在单线程环境中，环境对我们的程序使用的所有这些技巧都是隐藏的，除了加快执行之外没有其他效果。)
    The Java Language Specification requires the JVM to maintain within thread as‐if‐serial semantics: as long as the program has the same result as if it were executed in program order in a strictly sequential environment, all these games are permissible. (Java语言规范要求JVM在线程中保持‐if‐串行语义:只要程序具有与在严格顺序的环境中按程序顺序执行时相同的结果，所有这些规则都是允许的。)
    And that's a good thing, too, because these rearrangements are responsible for much of the improvement in computing performance in recent years. Certainly higher clock rates have contributed to improved performance, but so has increased parallelism ‐ pipelined superscalar execution units, dynamic instruction scheduling, speculative execution, and sophisticated multilevel memory caches. As processors have become more sophisticated, so too have compilers, rearranging instructions to facilitate optimal execution and using sophisticated global register‐allocation algorithms. And as processor manufacturers transition to multicore processors, largely because clock rates are getting harder to increase economically, hardware parallelism will only increase.