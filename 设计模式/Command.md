---
title:Command 命令模式
---

# Command 命令模式
**对象行为型模式**

## 意图
+ 将一个请求封装为一个对象，从而使你可用不同的请求对客户进行参数化；对请求排队记录请求日志，以及支持可撤销的操作。

## 动机
+ 有时候必须向某对象提交请求，但并不知道关于被请求的操作或请求的接收者的任何信息。例如，用户界面工具箱包括按钮和菜单这样的对象，它们执行请求响应用户输入。但工具箱不能显式的在按钮或菜单中实现该请求，因为只有使用工具箱的应用知道该由哪个对象做哪个操作。而工具箱的设计者无法知道请求的接受者或执行的操作。
+ 命令模式通过将请求本身变成一个对象来使工具箱对象可向未指定的应用对象提出请求。这个对象可被存储并像其他对象一样被传递。这一模式的关键是一个抽象的Command类，它定义了一个执行操作的接口。其最简单的形式是一个抽象的Execute操作，指定接收者采取的动作。而接收者有执行该请求所需的具体信息。

## 适用性
+ 


~~~ java
public interface Command {
    void execute();
}

public class ConcreateCommandOne implements Command {
    private String arg;

    public ConcreateCommandOne(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute() {
        System.out.println("#1 process... " + arg);
    }
}

public class ConcreateCommandTwo implements Command {
    //...
}

public class MacroCommand implements Command {
    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void execute() {
        for (Command c : commands) {
            c.execute();
        }
    }
}

public class CommandMain {
    public static void main(String[] args){
        ConcreateCommandOne commandOne=new ConcreateCommandOne("Arg ###");
        ConcreateCommandTwo commandTwo=new ConcreateCommandTwo("Arg $$$");

        MacroCommand command=new MacroCommand();
        command.addCommand(commandOne);
        command.addCommand(commandTwo);
        command.execute();
    }
}
~~~