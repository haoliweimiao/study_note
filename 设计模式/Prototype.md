---
title:Prototype 原型模式
---

# Prototype 原型模式

## 意图
+ 用原型实例指定创建对象的种类，并且通过拷贝(深克隆)这些原型创建新的对象。

## 动机
+ 在软件系统中，经常面临着“某些结构复杂的对象“的创建工作；由于需求的变化，这些对象经常面临着剧烈的变化，但是它们却拥有比较稳定一致的接口。

## 代码
~~~ java
public interface SplitterImpl{
    //通过克隆自己来创建对象
    SplitterImpl cloneSplitter();
    void split();
}

public class BinarySplitter implements SplitterImpl{
    @Override
    public SplitterImpl cloneSplitter(){
        return new BinarySplitter(this);
    }
}

public class TextSplitter implements SplitterImpl{
    @Override
    public SplitterImpl cloneSplitter(){
        return new TextSplitter(this);
    }
}

public class Test{
    private SplitterImpl impl;

    public Test(SplitterImpl impl){
        this.impl = impl;
    }

    public void run(){
        assert impl != null;
        SplitterImpl mSplitterImpl = impl.cloneSplitter();
        mSplitterImpl.split();
    }
}
~~~

## 总结
+ Prototype模式同样用于隔离类对象的使用者和具体类型(易变类)之间的耦合关系，它同样要求这些”易变类“拥有”稳定的接口“。
+ Prototype模式对于”如何创建易变类的实体对象“采用”原型克隆”的方法来做，它使得我们可以非常灵活地动态创建“拥有某些稳定接口的新对象——所需工作仅仅是注册一个新类的对象(即原型)，然后在任何需要的地方Clone。
+ Prototype模式中的Clone方法可以利用某些框架中的序列化来实现深拷贝。