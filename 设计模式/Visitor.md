---
title:Visitor 访问器模式
---

# Visitor 访问器模式
**对象行为型模式**

## 意图
+ 表示一个作用于某对象结构中的各元素的操作。它使你可以在不改变各元素的类的前提下定义作用于这些元素的新操作。

## 动机
+ **在软件构建过程中，由于需求的改变，某些层次结构中常常需要增加新的行为(方法)，如果直接在基类中做这样的更改，将会给子类带来很繁重的变更负担，甚至破坏原有设计。**

## 适用性
+ 一个对象结构包含很多类对象，它们有不同的接口，而你想对这些对象实施一些依赖与某具体类的操作。
+ 需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而你想避免这些操作“污染”这些对象的类。Visitor使得你可以将相关的操作集中起来定义在一个类中。当该对象结构被很多应用共享时，用Visitor模式让每个应用仅包含需要用的的操作。
+ 定义对象结构的类很少变化，但是经常需要在此结构上定义新的操作。改变对象结构类需要重定义对所有访问者的接口，这可能需要很大的代价。如果对象结构类经常改变，那么还是在这些类中定义这些操作较好。

## 参与者
+ Visitor
  + 为该对象结构中ConcreteElement的每一个类声明一个Visit操作。该操作的名称和特征标识了发送Visit请求给该访问者的那个类。这使得访问者可以确定正被访问元素的具体的类。这样访问者就可以通过该元素的特定接口直接访问它。
+ ConcreteVisitor
  + 实现由每个Visitor声明的操作，每个操作实现本算法的一部分，而该算法片段乃是对应于结构中对象的类。ConcreteVisitor为该算法提供了上下文并存储它的局部状态。这一状态常常在遍历该结构的过程中积累结果。
+ Element
  + 定义一个Accept操作，它以一个访问者为参数。
+ ConcreteElement
  + 实现Accept操作，该操作以一个访问者为参数。
+ ObjectStructure
  + 能枚举它的元素。
  + 可以提供一个高层的接口以允许该访问者访问它的元素。
  + 可以是一个复合或者是一个集合，如一个列表或者一个无序集合。


~~~ java
public interface Element {
    void accept(Visitor visitor);
}

public class ElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitorElementA(this);
    }
}

public class ElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitorElementB(this);
    }
}

public abstract class Visitor {
    public abstract void visitorElementA(ElementA elementA);

    public abstract void visitorElementB(ElementB elementB);
}


public class VisitorOne extends Visitor {
    @Override
    public void visitorElementA(ElementA elementA) {
        System.out.println("Visitor One is processing elementA");
    }

    @Override
    public void visitorElementB(ElementB elementB) {
        System.out.println("Visitor One is processing elementB");
    }
}

public class VisitorTwo extends Visitor {
    //...
}

public class VisitorMain {
    public static void main(String[] args){
        VisitorTwo visitorTwo=new VisitorTwo();
        ElementB elementB=new ElementB();
        elementB.accept(visitorTwo);

        ElementA elementA=new ElementA();
        elementA.accept(visitorTwo);
    }
}

~~~

## 注意
+ Visitor,Element类确定，Element子类确定(稳定)
+ Visitor实现类可变更，新增(不稳定)