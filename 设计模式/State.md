---
title:State 状态模式
---

# State 状态模式
**对象行为型模式**
*别名 状态对象(Objects for States)*

## 意图
+ 允许一个对象在其内部状态改变时改变它的行为。对象看起来似乎修改了它的类。

## 动机
+ 考虑一个表示网络连接的类TCPConnection。一个TCPConnection对象的状态处于若干不同的状态之一：连接已经建立(Established)、正在监听(Listening)、连接已关闭(Closed)。当一个TCPConnection对象收到其他对象的请求时，它更具自身的状态作出不同的反应。例如，一个Open请求的结果依赖于该连接是处于已关闭状态还是连接已经建立的状态。State模式描述了TCPConnection如何在每一种状态下表现出不同的行为。

## 适用性
+ 一个对象的行为取决于它的状态，并且它必须在运行时刻根据状态改变它的行为。
+ 一个操作中含有庞大的多分支的条件语句，且这些分支依赖于该对象的状态。这个状态通常用一个或者多个枚举常量表示。通常，有多个操作包含这一相同的条件结构。State模式将每一个条件分支放入一个独立的类中。这使得你可以根据对象自身的情况将对象的状态作为一个对象，这一对象可以不依赖与其他对象而独立变化。


## 参与者
+ Context(环境)
  + 定义客户感兴趣的接口
  + 维护一个Concrete子类的实例，这个实例定义当前状态。
+ State(状态)
  + 定义一个接口以封装与Context的一个特定状态相关的行为。
+ ConcreteState subclasses
  + 每一个子类实现一个与Context的一个状态相关的行为。

~~~ java
/**
 * 网络状态
 */
public enum NetworkState {
    network_open,
    network_close,
    network_connect;
}

public class NetworkProcessor {
    private NetworkState state;

    public void operation1() {
        if (state == NetworkState.network_open) {
            //...
            state = NetworkState.network_close;
        } else if (state == NetworkState.network_close) {
            //...
            state = NetworkState.network_connect;
        } else if (state == NetworkState.network_connect) {
            //...
            state = NetworkState.network_open;
        }
    }

    public void operation2() {}
    public void operation3() {}
}
~~~

## 修改
~~~ java
public abstract class NetWorkState {
    protected NetWorkState next;
    abstract void operation1();
    abstract void operation2();
    abstract void operation3();
}

public class OpenState extends NetWorkState {
    public static NetWorkState netWorkState;

    public static NetWorkState getInstance() {
        if (netWorkState == null) {
            netWorkState = new OpenState();
        }
        return netWorkState;
    }

    @Override
    void operation1() {
        //...
        next = CloseState.getInstance();
    }

    @Override
    void operation2() {
        //...
        next = ConnectState.getInstance();
    }

    @Override
    void operation3() {
        //...
        next = OpenState.getInstance();
    }
}

public class ConnectState extends NetWorkState {}

public class CloseState extends NetWorkState {}

public class NetworkProcessor {
    private NetWorkState state;

    public NetworkProcessor(NetWorkState state) {
        this.state = state;
    }

    void operation1() {
        //...
        state.operation1();
        state = state.next;
    }

    void operation2() {
        //...
        state.operation2();
        state = state.next;
    }

    void operation3() {
        //...
        state.operation3();
        state = state.next;
    }
}
~~~