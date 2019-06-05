---
title:Composite 组合模式
---

# Composite 组合模式
**对象结构型模式**

## 意图
* 将对象组合成树型结构以表示“部分-整体”的层次结构。Composite使得用户对单个对象和组合对象使用具有一致性。

## 动机
* 在绘图编辑器和徒刑捕捉系统这样的图形应用程序中，用户可以使用简单的组件创建复杂的图表。用户可以组合多个简单的组件以形成一个较大的组件，这些组件又可以组合成更大的组件。一个简单的实现方法是为Text和Line这样的图元定义一些类，另外定义一些类作为这些图元的容器类(Container)。
* 然而这种方法存在一个问题：使用这些代码必须区别对待图元对象和容器对象，而实际大多情况下用户认为它们是一样的。对这些类区别使用，使得程序更加复杂。Composite模式描述了如何使用递归组合，使得用户不必对这些类进行区别。
  * Graphic{draw(); add(Graphic g); remove(Graphic g); getChild(int);}
    * Line{draw();}
    * Rectandle{draw();}
    * Text{draw();}
    * Picture{draw(); add(Graphic g); remove(Graphic g); getChild(int);}
      * Picture draw():forall g in graphics g.draw();
      * Picture add():add g to list of graphics;
* Composite模式的关键是一个抽象类，它既可以代表图元，又可以代表图元的容器。在图形系统中这个类就是Graphic，它声明一些与特定图形对象的相关操作，例如draw。同时它也声明了所有组合对象共享的一些操作，例如一些操作用于访问和管理它的子部件。
* 子类Line、Rectangle和Text定义了一些图元对象，这些类实现了draw，分别用于绘制直线、矩形、正文。由于图元都没有子图形，因此它们都不执行与子类有关的操作。
* Picture类定义了一个Graphic对象的聚合。Picture和draw操作都是通过对它的子部件调用draw实现的，Picture还用这种方法实现了一些与子部件相关的操作。由于Picture和Graphic接口是一致的，因此Picture对象可以递归地组合其他Picture对象。

## 适用性
* 你想表示对象的部分-整体结构层次
* 你希望用户忽略组合对象与单个对象的不同，用户将统一地使用组合结构中的所有对象。
  
## 参与者
* Component(Graphic)
  * 为组合中的对象声明接口
  * 在适当的情况下，实现所有类共有接口的缺省行为。
  * 声明一个接口用于访问和管理Component的子组件。
  * (可选)在递归结构中定义一个接口，用于访问一个父部件，并在合适的情况下实现它。
* Leaf(Rectangle、Line、Text等)
  * 在组合中表示叶节点对象，叶节点没有子节点。
  * 在组合中定义图元对象的行为。
* Composite(Picture)
  * 定义有子部件的那些部件的行为。
  * 存储子部件
  * 在Component接口中实现与子部件有关的操作。
* Client
  * 通过Component接口操纵组合部件的对象。
  
## 协作
* 用户使用Component类接口与组合结构中的对象进行交互。如果接受者是一个叶节点，则直接处理请求。如果接受者是Composite，它通常将请求发送给它的子部件，在转发之前、之后可能执行一些辅助操作。

## 效果
* **定义了包含基本对象和组合的类层次结构** 
  * 基本对象可以被组合成更复杂的组合对象，而这个对象又可以被组合，这样不断的递归下去。客户代码中，任何用到基本对象的地方都可以使用组合对象。
* **简化客户代码** 
  * 客户可以一致地使用组合结构和单个对象。通常用户也不知道(不关心)处理的是一个叶节点还是一个组合组件。这样就简化了客户代码，因为在定义组合的那些类中不需要写一些充斥着选择语句的函数。
* **使得更容易增加新类型的组件** 
  * 新定义的Composite或Leaf子类自动地与已有的结构和客户代码一起工作，客户程序不需因新的Component类而改变
* **使你的设计变得更加一般化**
  * 容易增加新组件也会产生一些问题，那就是很难限制组合中的组件。有时候你希望某个特定的组合只能有某些特定的组件。使用Composite时，你不能依赖类型系统施加这些约束，而必须在运行时刻进行检查。

~~~ java
public abstract class Component {
    abstract void process();
}

public class Composite extends Component {

    private String name;

    private List<Component> elements=new ArrayList<>();

    public Composite(String name) {
        this.name = name;
    }

    void add(Component element){
        elements.add(element);
    }

    void remove(Component element){
        elements.remove(element);
    }

    @Override
    void process() {
        //process current node

        //process leaf node
        for (Component c : elements) {
            c.process();
        }
    }
}

public class Leaf extends Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    void process() {
        //process current node
    }

    void invoke(Component c){
        //...
        c.process();
        //...
    }
}

public class CompositeMain {
    public static void main(String[] args) {
        Composite root = new Composite("root");
        Composite treeNode1 = new Composite("treeNode1");
        Composite treeNode2 = new Composite("treeNode2");
        Composite treeNode3 = new Composite("treeNode3");
        Composite treeNode4 = new Composite("treeNode4");

        Leaf leaf1 = new Leaf("leaf1");
        Leaf leaf2 = new Leaf("leaf1");

        root.add(treeNode1);
        treeNode1.add(treeNode2);
        treeNode2.add(leaf1);

        root.add(treeNode3);
        treeNode3.add(treeNode4);
        treeNode4.add(leaf2);

        root.process();
    }
}

~~~