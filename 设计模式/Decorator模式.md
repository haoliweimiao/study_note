---
title:装饰模式 Decorator
---

# 装饰模式 Decorator

使用组合替代继承

## 动机
~~~ xml
在某些情况下我们可能“过度地使用继承来扩展对象的功能“，由于继承为类型引入的静态特质，使得这种扩展方式缺乏灵活性；并随着子类的增多（扩展功能增多），各种子类的组合（扩展功能的组合）会导致更多子类的膨胀。
~~~

## 模式定义
~~~ xml
动态的给一个对象增加一些额外的职责。就增加功能而言，Decorator模式比继承更为灵活(消除重复代码，减少子类个数)。
~~~

## 示例
~~~ java
Stream.java
public class Stream{
    char read(int number);
    void seek(int position);
    void write(char data);
}

FileStream.java
public class FileStream implements Stream{
    @Override
    public char read(int number){
        //读文件流
    }
    @Override
    public void seek(int position){
        //定位文件流
    }
    @Override
    public void write(char data){
        //写文件流
    }
}

NetStream.java
public class NetStream implements Stream{
    @Override
    public char read(int number){
        //读网络流
    }
    @Override
    public void seek(int position){
        //定位网络流
    }
    @Override
    public void write(char data){
        //写网络流
    }
}

MemoryStream.java
public class MemoryStream implements Stream{
    @Override
    public char read(int number){
        //读内存流
    }
    @Override
    public void seek(int position){
        //定位内存流
    }
    @Override
    public void write(char data){
        //写内存流
    }
}

CrypToFileStream.java
public class CrypToFileStream extends FileStream{
    @Override
    public char read(int number){
        //读文件流
        super.read(number);
        //额外的加密操作
        return new char();
    }
    @Override
    public void seek(int position){
        //定位文件流
        super.seek(position);
        //额外的加密操作
    }
    @Override
    public void write(char data){
        //写文件流
        super.write(data);
        //额外的写入操作
    }
}

CrypToNetStream.java
public class CrypToNetStream extends NetStream{
    @Override
    public char read(int number){
        //读网络流
        super.read(number);
        //额外的加密操作
        return new char();
    }
    @Override
    public void seek(int position){
        //定位网络流
        super.seek(position);
        //额外的加密操作
    }
    @Override
    public void write(char data){
        //写网络流
        super.write(data);
        //额外的写入操作
    }
}
~~~

修改
~~~ java
CrypToStream.java
public class CrypToStream{
    private Stream stream;
    
    public CrypToStream(Stream stream){
        this.stream = stream;
    }

    @Override
    public char read(int number){
        //读stream流
        stream.read(number);
        //额外的加密操作...
        return new char();
    }
    @Override
    public void seek(int position){
        //定位stream流
        stream.seek(position);
        //额外的加密操作
    }
    @Override
    public void write(char data){
        //写stream流
        stream.write(data);
        //额外的写入操作
    }
}
~~~

Decorator模式修改
~~~ java
DecoratorStream.java
public class DecoratorStream{
    protected Stream stream;

    public DecoratorStream(Stream stream){
        this.stream = stream;
    }
}

CrypToStream.java
public class CrypToStream extends DecoratorStream{
    
    
    public CrypToStream(Stream stream){
        this.stream = stream;
    }

    @Override
    public char read(int number){
        //读stream流
        stream.read(number);
        //额外的加密操作...
        return new char();
    }
    @Override
    public void seek(int position){
        //定位stream流
        stream.seek(position);
        //额外的加密操作
    }
    @Override
    public void write(char data){
        //写stream流
        stream.write(data);
        //额外的写入操作
    }
}
~~~