---
title:观察者模式
---
# 观察者模式(Observer)

## 意图
~~~ xml
定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并自动更新。
~~~

## 未使用
~~~ java
MainForm.java
public class MainForm extend Form{
    private TextBox txtFilePath;
    private TextBox txtFileNumber;
    private ProcessBar processBar;

    void Button_click(){
        String filepath = txtFilePath.gettext();

        int number = Integer.valueOf(txtFileNumber.gettext());

        Filesplitter splitter=new Filesplitter(filepath,number,processBar);

        splitter.split();
    }
}

Filesplitter.java
public class Filesplitter{
    private String mFilePath;
    private int mFileNumber;
    private ProcessBar mProcessBar

    public Filesplitter(String filePath,int fileNumber,ProcessBar mProcessBar){
        this.mFilePath=filePath;
        this.mFileNumber=fileNumber;
        this.mProcessBar=mProcessBar;
    }

    public void spilt(){
        //1.读取大文件

        //2.分批次向小文件写入
        for(int i=0,i<mFileNumber;i++){
            //...
            //违背依赖倒置原则，具体实现应该依赖于抽象
            if(mProcessBar!=null){
                mProcessBar.setValue((i+1)/mFileNumber);
            }
        }
    }
}
~~~


## 使用
~~~ java
MainForm.java
public class MainForm extend Form{
    private TextBox txtFilePath;
    private TextBox txtFileNumber;
    private ProcessBar processBar;

    void Button_click(){
        String filepath = txtFilePath.gettext();

        int number = Integer.valueOf(txtFileNumber.gettext());

        ProcessInterface mprocessInterface=new mprocessInterface{
            @Override
            void onProcess(float process){
                processBar.setValue(process);
            }
        }

        Filesplitter splitter=new Filesplitter(filepath,number);

        splitter.addListener(mprocessInterface);

        splitter.split();
    }
}

ProcessInterface.java
public interface ProcessInterface{
    void onProcess(float process);
}

Filesplitter.java
public class Filesplitter{
    private String mFilePath;
    private int mFileNumber;
    private List<ProcessInterface> mProcessInterfaces

    public Filesplitter(String filePath,int fileNumber){
        this.mFilePath=filePath;
        this.mFileNumber=fileNumber;
    }

    public void addListener(ProcessInterface processInterface){
        mProcessInterfaces.add(processInterface);
    }

    public void removeListener(ProcessInterface processInterface){
        mProcessInterfaces.remove(processInterface);
    }

    public void spilt(){
        //1.读取大文件

        //2.分批次向小文件写入
        for(int i=0,i<mFileNumber;i++){
            //...
            //违背依赖倒置原则，具体实现应该依赖于抽象
            if(mProcessInterfaces!=null){
                for(ProcessInterfaces interface: mProcessInterfaces)
                interface.process((i+1)/mFileNumber);
            }
        }
    }
}
~~~