---
title:Bridge模式
---

# Bridge模式

## 动机
~~~ xml
由于某些类型的固有的实现逻辑，使得它们具有两个变化的维度，乃至多个维度的变化。
~~~

## 模式定义
~~~ xml
将抽象部分（业务功能）与实现部分（平台实现）分离，使它们都可以独立的变化。
~~~

## 示例
~~~ java
Message.java
public interface Message{
    void login(String username,String password);
    void sendMessage(String message);
    void sendPicture(Image image);

    void playSound();
    void drawShape();
    void writeText();
    void connect();
}

PCMessage.java
public abstract class PCMessage implements Message{
    //PC平台实现
    //...实现部分方法
    void playSound(){}
    void drawShape(){}
    void writeText(){}
    void connect(){}
}

MobileMessage.java
public abstract class MobileMessage implements Message{
    //手机平台实现
    //...实现部分方法
    void playSound(){}
    void drawShape(){}
    void writeText(){}
    void connect(){}
}

PCMessageLite.java
public class PCMessageLite implements Message{
    //PC平台实现

    @Override
    public void login(String username,String password){
        connect();
        //后续登录操作
    }

    @Override
    public void sendMessage(String message){
        writeText();
        //后续发送操作
    }

    @Override
    public void sendPicture(Image image){
        writeText();
        //后续发送操作
    }
}

PCMessagePrefect.java
public class PCMessagePrefect implements Message{
    //PC平台实现

    @Override
    public void login(String username,String password){
        playSound();
        connect();
        //后续登录操作
    }

    @Override
    public void sendMessage(String message);{
        playSound();
        writeText();
        //后续发送操作
    }

    @Override
    public void sendPicture(Image image){
        playSound();
        writeText();
        //后续发送操作
    }
}

MobileMessageLite.java
...
MobileMessagePrefect.java
...
~~~

## 修改示例
~~~ java
MessageImpl.java
public interface MessageImpl{
    private MessageOtherImpl impl;

    void login(String username,String password);
    void sendMessage(String message);
    void sendPicture(Image image);
}

MessageOtherImpl.java
public interface MessageOtherImpl{
    void playSound();
    void drawShape();
    void writeText();
    void connect();
}

PCMessageOther.java
public abstract class PCMessageOther implements MessageOtherImpl{
    //PC平台实现
    //...实现部分方法
    void playSound(){}
    void drawShape(){}
    void writeText(){}
    void connect(){}
}

MobileMessage.java
public abstract class MobileMessageOther implements MessageOtherImpl{
    //手机平台实现
    //...实现部分方法
    void playSound(){}
    void drawShape(){}
    void writeText(){}
    void connect(){}
}

PCMessageLite.java
public class PCMessageLite implements MessageImpl{
    //PC平台实现
    public PCMessageLite(MessageOtherImpl impl){
        this.impl = impl;
    }

    @Override
    public void login(String username,String password){
        impl.connect();
        //后续登录操作
    }

    @Override
    public void sendMessage(String message){
        impl.writeText();
        //后续发送操作
    }

    @Override
    public void sendPicture(Image image){
        impl.writeText();
        //后续发送操作
    }
}

PCMessagePrefect.java
public class PCMessagePrefect implements MessageImpl{
    //PC平台实现
    public PCMessagePrefect(MessageOtherImpl impl){
        this.impl = impl;
    }

    @Override
    public void login(String username,String password){
        impl.playSound();
        impl.connect();
        //后续登录操作
    }

    @Override
    public void sendMessage(String message);{
        impl.playSound();
        impl.writeText();
        //后续发送操作
    }

    @Override
    public void sendPicture(Image image){
        impl.playSound();
        impl.writeText();
        //后续发送操作
    }
}

Main.java
public class Main{
    public static void main(String[] args){
        MessageOthreImpl impl = new PCMessageOther();
        PCMessagePrefect pcMsg = new PCMessagePrefect(impl);
        pcMsg.login("username","pwd");
    }
}

~~~

## 模式总结
~~~ xml
1. Bridge模式使用”对象间的组合关系“解耦了抽象和实现之间固有的绑定关系，使得抽象和实现可以沿着各自的维度来变化。所谓的抽象和实现沿着各自维度变化，即”子类化“它们。
2. Bridge模式有时候类似于多继承方案，但是多继承方案违背单一职责原则（即一个类只有一个变化的原因），复用性比较差。Bridge模式是比多继承方案更好的解决方案。
3. Bridge模式应用一般在”两个非常强的变化维度“，有时一个类也多于两个的变化维度，这时可以使用Bridge的拓展模式。
~~~