---
title:共享对象-可见性
---

# 共享对象-可见性

## 共享变量错误示例

***

#### 在没有同步的情况下共享变量

    public class NoVisibility{
        private static boolean ready;
        private static int number;

        private static class ReaderThread extends Thread{
            public void run(){
                while(!ready)
                    Thread.yield();
                System.out.println(number);
            }
        }

        public static void main(String[] args){
            new ReaderTheard().start();
            number = 42;
            ready = true;
        }
    }

***

    在没有同步的情况下，编译器，处理器，运行时安排操作的执行顺序可能完全出人意料。在没有进行适当同步的多线程程序中，尝试推断那些“必然”发生在内存中的动作时，你总是会判断错误。