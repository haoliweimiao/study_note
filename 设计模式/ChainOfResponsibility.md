---
title:Chain Of Responsibility 职责链
---

# Chain Of Responsibility 职责链
**对象行为型模式**

## 意图
+ 使多个对象都有机会处理请求，从而避免请求的发送者和接收者之间的耦合关系。将这些对象连成一个链，并沿着这条链传递该请求，直到有个对象处理它为止。


## 动机
+ **在软件构造过程中，一个请求可能被多个对象处理，但是每个请求在运行时只能有一个接收者，如果显式指定，将必不可少地带来请求发送者与接收者的紧耦合**
+ 考虑一个图形用户中的上下文有关的帮助机制。用户在界面的任一部分上点击就可以得到帮助信息，所提供的帮助依赖于点击的是界面的哪一部分以及其上下文。例如，对话框中的按钮的帮助信息就可能和主窗口中类似的按钮不同。如果对那一部分界面没有特定的帮助信息，那么帮助系统应该显示一个关于当前上下文的较一般的帮助信息--比如说，整个对话框。
+ 因此很自然地，应根据普遍性(generality)即从最特殊到最普遍的顺序来组织帮助信息。而且，很显然，在这些用户界面对象中会有一个对象来处理帮助请求：至于是哪一个对象则取决于上下文以及可用的帮助具体到何种程度。
+ 这儿的问题是提交帮助请求的对象(如按钮)并不明确知道谁是最终提供帮助的对象。我们要有一种方法将提交帮助请求的对象与可能提供帮助信息的对象解耦(decouple)。Chain of Responsibility模式告诉我们应该怎么做。
+ 这一模式的想法是，给多个对象处理一个请求的机会，从而解耦发送者和接收者。该请求沿着对象链传递直至其中一个对象处理它。(类似Android中的事件分发，消费事件一直向下传递，直至消费)

## 适用性
**以下条件下使用Responsibility链：**
+ 有多个对象可以处理同一个请求，哪个对象处理该请求运行时刻自动决定
+ 你想在不明确指定接收者的情况下，向多个对象中的一个提交一个请求
+ 可处理一个请求的对象集合应被动态指定

## 参与者
+ Handler
  + 定义一个处理请求的接口
  + (可选)实现后续链
+ ConcreteHandler
  + 处理它所负责的请求
  + 可访问它的后继者
  + 如果可处理该请求，就处理；否则将该请求转发给它的后继者
+ Client
  + 向链上的具体处理者(ConcreteHandler)对象提交请求


## 协作
+ 当客户提交一个请求的时候，请求沿链传递直到有一个ConcreteHandler对象负责处理它。


## 效果
**Responsibility有以下优缺点**
+ **降低耦合度** 该模式使得一个对象无需知道其它哪一个对象处理其请求。对象仅知道该请求会被“正确”地处理。接收者和发送者之间都没有对方明确的信息，而且链中的对象不需要知道链的结构。
  <br>结果是，职责链可简化对象间的相互连接。它们仅需保持一个指向后续其后继者的引用，而不需要保持它所有的候选接收者的引用。
+ **增强了给对象指派责任(Responsibility)的灵活性** 在对象中分派职责时，职责链给你更多地灵活性。你可以通过在运行时刻对该链进行动态的增加或者修改来增加或者改变处理一个请求的那些职责。。可以将这些机制与静态的特例化处理对象的继承机制结合起来。
+ **不保证被接受** 既然一个请求没有明确的接收者，那么就不能保证它一定会被处理--该请求可能一直到链的末端都得不到处理。一个请求也可能因该链没有正确配置而得不到处理。

~~~java
public enum RequestType {

    REQ_HANDLER1(1),REQ_HANDLER2(2), REQ_HANDLER3(3);

    private int type;

    RequestType(int type) {
        this.type = type;
    }
}

public class Request {
    private String description;
    private RequestType reqType;

    public Request(String description, RequestType reqType) {
        this.description = description;
        this.reqType = reqType;
    }

    public RequestType getReqType() {
        return reqType;
    }

    public String getDescription() {
        return description;
    }
}


public abstract class ChainHandler {
    private ChainHandler nextChain;

    abstract boolean canHandleRequest(final Request request);

    abstract void processRequest(final Request request);

    public ChainHandler() {
        this.nextChain = null;
    }


    public void setNextChain(ChainHandler nextChain) {
        this.nextChain = nextChain;
    }

    private void sendRequestToNextHandler(final Request request) {
        if (nextChain != null) {
            nextChain.handle(request);
        }
    }


    public void handle(Request request) {
        if (canHandleRequest(request)){
            processRequest(request);
        }else {
            sendRequestToNextHandler(request);
        }
    }
}

public class HandlerOne extends ChainHandler {
    @Override
    boolean canHandleRequest(Request request) {
        return request.getReqType() == RequestType.REQ_HANDLER1;
    }

    @Override
    void processRequest(Request request) {
        System.out.println("Handler One is handle request: " + request.getDescription());
    }
}

public class HandlerTwo extends ChainHandler {
    //...
}

public class HandlerThree extends ChainHandler {
    //...
}

public class ChainOfReponsibilityMain {
    public static void main(String[] args) {
        HandlerOne h1 = new HandlerOne();
        HandlerTwo h2 = new HandlerTwo();
        HandlerThree h3 = new HandlerThree();

        h1.setNextChain(h2);
        h2.setNextChain(h3);

        Request request = new Request("process task ...", RequestType.REQ_HANDLER3);
        h1.handle(request);
    }
}


~~~