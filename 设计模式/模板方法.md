---
title:模板方法 template method--类行为型模式
---
# 模板方法

* 定义一个操作中算法的骨架(稳定)，而将一些步骤延时到子类(变化)中。Template Method使得子类可以不改变(复用)一个算法的结构即可重定义该算法的某些特定的步骤。


## 未使用模板方法
~~~ java
    Library.java
    public class Library{

        public void step1(){
            //doing something
        }

        public void step3(){
            //doing something
        }

        public void step5(){
            //doing something
        }
    }

    public class Application{
        public bool step2(){
            //doing something
        }

        public void step4(){
            //doing something
        }
    }

    Main.java
    public void Main{
        public static void main(String[] args){
            Library lib = new Library();
            Application app=new Application();

            lib.step1();

            if(app.step2()){
                lib.step3();
            }

            for(int i = 0;i < 4;i++){
                app.step4();
            }
            lib.step5();
        }
    }
~~~

---

## 使用模板方法
~~~ java
    Library.java
    public abstract class Library{

        //稳定
        public void run(){
            step1();

            if(step2()){
                step3();
            }

            for(int i = 0;i < 4;i++){
                step4();
            }
            step5();
        }

        private void step1(){
            //doing something
        }

        //变化
        public bool step2();

        private void step3(){
            //doing something
        }

        //变化
        public void step4();

        private void step5(){
            //doing something
        }
    }

    Application.java
    public class Application extends Library{
        @Override
        public bool step2(){
            //doing something
        }

        @Override
        public void step4(){
            //doing something
        }
    }

    Main.java
    public void Main{
        public static void main(String[] args){
            Application app=new Application();
            app.run();
        }
    }
~~~