---
title:Factory Method
---

# 工厂模式 Factory Method

## 意图
~~~ xml
定义一个用于创建对象的接口，让子类决定实例化哪一个类。Factory Method使一个类的实例化延时到其子类。
具体表现为：需求的变化，经常面临创建对象的工作；由于需求的变化，需要创建的对象的具体类型经常变化。
~~~

## 代码
~~~ java
public interface Splitter{
    void splitter();
}

public class BinarySplitter implements Splitter{

}

public class VideoSplitter implements Splitter{

}

public class TextSplitter implements Splitter{

}

public class Test{
    public void run(){
        //使用new创建了具体类的对象，依赖了具体类（紧耦合）
        Splitter splitter = new BinarySplitter();
        splitter.splitter();
    }
}
~~~

## 修改代码
~~~ java
public abstract class SplitterFactory{
    Splitter createSplitter();
}

public class BinarySplitterFactory extends SplitterFactory{
    @Override
    public Splitter createSplitter(){
        return new BinarySplitter();
    }
}


public class Test{
    private SplitterFactory factory;

    public Test(SplitterFactory factory){
        this.factory = factory;
    }

    public void run(){
        //未使用new方法，将创建交给外部传入的工厂类实现，由编译时依赖->运行时依赖？
        Splitter splitter = factory.createSplitter();;
        splitter.splitter();
    }
}
~~~