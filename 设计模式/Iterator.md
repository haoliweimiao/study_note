---
title:Iterator 迭代器
---

# Iterator 迭代器
**对象行为型模式**

## 意图
+ 提供一种方法顺序访问一个聚合对象中的各个元素，而又不暴露该对象的内部表示。

## 动机
+ 一个聚合对象，例如列表(list)，应该提供一个方法来让别人可以访问它的元素，而又不需要暴露它的内部结构。此外，针对不同的需要，可能要以不同的方式遍历这个列表。但是即使可以预见到所需的那些遍历操作，你可能也不希望列表接口中充斥着各种不同遍历的操作。有时还可能需要在同一个表列上进行多个遍历。
+ 迭代器模式可以帮你解决所有这些问题。这一模式关键思想就是将对列表的访问和遍历从对象列表中分离出来并放入一个迭代器(iterator)对象中。迭代器定义了一个访问该列表元素的接口。迭代器对象负责跟踪当前的元素；即，它知道哪些元素已经遍历过了。

## 适用性
+ 访问一个聚合对象而无需暴露它的内部表示。
+ 支持聚合对象的多种遍历。
+ 为遍历不同的聚合结构提供一个统一的接口(即，支持多态迭代)。

## 参与者
+ Iterator(迭代器)
  + 迭代器定义访问和遍历元素的接口
+ ConcreteIterator(具体迭代器)
  + 具体迭代器实现迭代器接口
  + 对该聚合遍历时跟踪当前位置
+ Aggregate(聚合)
  + 聚合定义创建相应迭代器对象的接口
+ ConcreteAggregate(具体聚合)
  + 具体聚合实现创建相应迭代器的接口，该操作返回ConcreteIterator的一个适当实例。
  
## 协作
+ ConcreteIterator跟踪聚合中的当前对象，并能够计算出待遍历的后续对象。


## 效果
+ **它支持以不同的方式遍历一个聚合** 复杂的聚合可用多种方式进行遍历。例如，代码生成和语义检查需要遍历语法分析树。代码生成可以按中序或者前序来遍历语法分析树。迭代器模式使得遍历算法变得很容易：仅需要一个不同的迭代器实例代替原先的实例即可。你也可以定义自己定义的迭代器的子类以支持新的遍历。
+ **迭代器简化了聚合的接口** 有了迭代器的遍历接口，聚合本身就不需要类似的遍历接口了。这样简化了聚合的接口。
+ **在同一个聚合上可以有多个遍历** 每个迭代器保持它自己的遍历状态。因此你可以同时进行多个遍历。

~~~ java
public interface Iterator<T> {
    void first();

    void next();

    boolean isDone();

    T current();
}

public class MyCollecton<T> {
    Iterator<T> getIterator(){
        return new Iterator<T>(){
            //...
        };
    }
}

public class CollectionIterator<T> implements Iterator<T> {
    private MyCollecton<T> mc;

    public CollectionIterator(MyCollecton<T> mc) {
        this.mc = mc;
    }

    @Override
    public void first() {

    }

    @Override
    public void next() {

    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public T current() {
        return null;
    }
}

public class IteratorMain {
    public static void main(String[] args) {
        MyCollecton<Integer> mc = new MyCollecton<>();

        Iterator<Integer> iterator = mc.getIterator();
        for (iterator.first(); !iterator.isDone(); iterator.next()) {
            System.out.println(iterator.current());
        }
    }
}
~~~