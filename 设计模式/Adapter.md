---
title:Adapter 适配器
---

# Adapter 适配器
类对象结构型模型

## 意图
+ 将一个类的接口转换成客户希望的另外一个接口。Adapter模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。

## 动机
+ 有时，为复用而设计的工具箱类不能够被复用的原因仅仅是因为它的接口与专业应用领域所需要的接口不匹配。
  
  例如，有一个绘图编辑器，这个编辑器允许用户绘制和排列基本图元（线，多边形和正文等）生成图片和图表。这个绘图编辑器的关键抽象是图形对象。图形对象有一个可编辑的形状，并可以绘制自身。图形对象的接口由一个称为Sharp的抽象类定义。绘图编辑器为每一种图形对象定义了一个Sharp的子类：LineSharp类对应直线，PolygonSharp类对应多边形等。
  像LineSharp和PolygonSharp这样的基本几何图形类比较容易实现，这是由于它们的绘图和编辑功能本来就有限。但是对于可以显示和编辑正文的TextSharp子类来说，实现相当困难，因为即使是基本的正文编辑也要设计到复杂的屏幕刷新和缓冲区管理。同时，成品的用户界面工具箱可能已经提供了一个复杂的TextView类用于显示和编辑正文，理想的情况是我们可以复用这个TextView类以实现TextSharp类，但是工具箱的设计者当时并没有考虑Sharp的存在，因此TextView和TextSharp对象不能互换。
  我们可以改变TextView类使它兼容Sharp类的接口，需要有这个工具箱的源码。定义一个TextSharp类，由它来适配TextView的接口和Sharp类的接口，可以使用以下方法做到：
  1. 继承Sharp类的接口和TextView类的实现
  2. 将一个TextView实例作为TextSharp的组成部分，并且使用TextView的接口实现TextSharp。
  
  这两种方法恰恰对应于Adapter模式的类和对象版本。我们将TextSharp称为适配器Adapter。

## 使用性
**以下情况使用Adapter模式**
+ 你想使用一个已经存在的类，而它的接口不符合你的要求。
+ 你想创建一个可以复用的类，该类可以与其他不相关的类或者不可预见的类（即那些接口可能不兼容的类）协同工作。
+ （仅适用于对象Adapter）你想使用一些已经存在的子类，但是不可能对每一个都进行子类化以匹配它们的接口。对象适配器可以适配它的父类接口。

## 参与者
+ Target
  + 定义Client使用的特定领域的相关接口。
+ Client
  + 与符合Target接口的对象协同
+ Adaptee
  + 定义一个已经存在的接口，这个接口需要适配。
+ Adapter
  + 对adaptee的接口和Target接口进行适配。

~~~ java
/**
 * 遗留接口（旧接口）
 */
public interface AdapteeImpl {
    void foo(int data);
    int bar();
}

/**
 * 遗留类型
 */
public class OldClass implements AdapteeImpl {

    @Override
    public void foo(int data) {

    }

    @Override
    public int bar() {
        return 0;
    }
}

/**
 * 目标接口（新接口）
 */
public interface TargetImpl {
    void process();
}

/**
 * 对象适配器
 */
public class TargetAdapter implements TargetImpl {
    protected AdapteeImpl adaptee;

    public TargetAdapter(AdapteeImpl adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void process() {
        assert adaptee != null;
        int data = adaptee.bar();
        adaptee.foo(data);
    }
}

/**
 * 类适配器(灵活性不够，不推荐使用)
 */
public class TargetClassAdapter implements AdapteeImpl, TargetImpl {
    @Override
    public void foo(int data) {
        //与Oldclass方法一致，写死一种
    }

    @Override
    public int bar() {
        //与Oldclass方法一致，写死一种
        return 0;
    }

    @Override
    public void process() {
        int data = bar();
        foo(data);
    }
}

public class AdapterMain {
    public static void main(String[] args) {
        AdapteeImpl adaptee = new OldClass();
        TargetImpl target = new TargetAdapter(adaptee);
        target.process();
    }
}
~~~
