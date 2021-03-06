---
title:策略模式 Strategy--对象行为型模式
---
# 策略模式 Strategy

## 意图
+ 定义一系列的算法，把它们一个个封装起来，并且使它们可相互替换。本模式使得算法可独立于使用它的客户而变化。

## 动机
许多算法对一个正文流进行分析。将这些算法硬编进使用它们的类中是不可取的，其原因如下：
* 需要换行功能的客户程序如果直接包含换行算法的代码的话将变得复杂，着使得客户程序庞大并且难以维护，尤其当其需要支持多种换行算法时间问题会更加严重。
* 不同的时候需要不同的算法，我们不想支持我们不想使用的换行算法。
* 当换行功能是客户程序的一个难以分割的成分时，增加新的换行算法或改变现有算法将十分困难。

## 适用性
+ 许多相关的类仅仅是行为有异。“策略”提供了一种用多个行为中的一个行为来配置一个类的方法。
+ 需要使用一个算法的不同变体。例如，你可能会定义一些反应不同的空间/时间权衡的算法。当这些变体实现为一个算法的类层次时[HO87]，可以使用策略模式。
+ 算法使用客户不应该知道的数据。可以使用策略模式以避免暴露复杂的、与算法相关的数据结构。
+ 一个类定义了多种行为，并且这些行为在这个类的操作中以多个条件语句的形式出现。将相关的条件分支移入它们各自的Strategy类中以替代这些条件语句。

## 参与者
+ Strategy
  + 定义所有支持的算法的公共接口。Context使用这个接口来调用某ConcreteStrategy定义的算法。

## 代码示例
~~~ java
TaxBase.java
public enum TaxBase{
    CN_Tax,
    US_Tax,
    DE_Tax,
    FR_Tax;//新增修改
}

SaleOrder.java
public class SaleOrder{
    TaxBase tax;

    public double calculateTax(Context context){
        if(tax == TaxBase.CN_Tax){

        }
        else if(tax == TaxBase.US_Tax){

        }
        else if(tax == TaxBase.DE_Tax){

        }
        //新增修改
        else if(tax == TaxBase.FR_Tax){

        }
    }
}
~~~

~~~ java
TaxStrategy.java
public abstract class TaxStrategy{
    public double calculate(Context context);
}

CNTax.java
public class CNTax{
    public void calculate(Context context){
        return **;
    }
}

USTax.java
public class USTax{
    public void calculate(Context context){
        return **;
    }
}

DETax.java
public class DETax{
    public void calculate(Context context){
        return **;
    }
}

FRTax.java
public class FRTax{
    public void calculate(Context context){
        return **;
    }
}

SaleOrder.java
public class SaleOrder{
    TaxStrategy tax;

    public SaleOrder(TaxFactory factory){
        tax = factory.create();
    }

    public double calculateTax(){
        //...
        tax.calculate(context);
    }
}
~~~