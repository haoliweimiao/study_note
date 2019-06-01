---
title:Builder 构建器
---

# Builder 构建器

## 意图
+ 将一个复杂对象的构建与它分离，使得同样的构建过程可以创建不同的表示

## 动机
+ 在软件系统中，有时候面临着”一个复杂对象“的创建工作，其通常由各个部分的子对象用一定的算法构成；由于需求的变化，这个复杂对象的各个部分经常面临着剧烈的变化，但是将它们组合在一起的算法却相对稳定。

## 适用性
+ 当创建复杂对象的算法应该独立于该对象的组成部分以及它们的装配方式时。
+ 当构造过程必须允许被构造的对象有不同的表示时。

## 代码
~~~ java
public abstract class House {
    protected abstract void buildPart1();
    protected abstract void buildPart2();
    protected abstract boolean buildPart3();
    protected abstract void buildPart4();
    protected abstract void buildPart5();
    
    public void init(){
        buildPart1();

        for(int i = 0; i < 4; i++){
            buildPart2();
        }

        boolean flag = buildPart3();

        if(flag){
            buildPart4();
        }

        buildPart5();
    }
}

public class StoneHouse extends House {
    @Override
    protected void buildPart1() {

    }

    @Override
    protected void buildPart2() {

    }

    @Override
    protected boolean buildPart3() {
        return false;
    }

    @Override
    protected void buildPart4() {

    }

    @Override
    protected void buildPart5() {

    }
}

public class BuilderMain {
    public static void main(String[] args) {
        House house = new StoneHouse();
        house.init();
    }
}
~~~

## 参与者
- Bulider
  - 为创建一个Product对象的各个部件指定抽象接口。
- ConcreteBuilder
  - 实现Bulider的接口以构造和装配该产品的各个部件
  - 定义并明确它所创建的表示
  - 提供一个检索产品的接口
- Director
  - 构造一个使用Builder接口的对象
- Product
  - 表示被构造的复杂对象。ConcreteBuilder创建该产品的内部表示并定义它的装配过程。
  - 包含定义组成部件的类，包括将这些部件装配成最终产品的接口。

## 修改代码
~~~ java
public class House{
    //...
}

public class HouseBuilder{
    protected mHouse;

    protected abstract void buildPart1();
    protected abstract void buildPart2();
    protected abstract boolean buildPart3();
    protected abstract void buildPart4();
    protected abstract void buildPart5();

    House getHouse(){
        return mHouse;
    }
}

public class StoneHouse extends House{
    //...
}

public class StoneBuilder entends HouseBuilder{
    @Override
    protected void buildPart1() {

    }

    @Override
    protected void buildPart2() {

    }

    @Override
    protected boolean buildPart3() {
        return false;
    }

    @Override
    protected void buildPart4() {

    }

    @Override
    protected void buildPart5() {

    }
}

public abstract class HouseDirector {

    private HouseBulider houseBulider;

    public HouseDirector(HouseBulider houseBulider) {
        this.houseBulider = houseBulider;
    }


    public House init(){
        houseBulider.buildPart1();

        for(int i = 0; i < 4; i++){
            houseBulider.buildPart2();
        }

        boolean flag = houseBulider.buildPart3();

        if(flag){
            houseBulider.buildPart4();
        }

        houseBulider.buildPart5();

        return houseBulider.getHouse();
    }
}

public class BuilderMain {
    public static void main(String[] args) {
        StoneHouseBulider stoneHouseBulider = new StoneHouseBulider();
        House house = new HouseDirector(stoneHouseBulider).init();
    }
}
~~~