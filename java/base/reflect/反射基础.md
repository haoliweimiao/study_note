# Java Reflect

+ [根据字符串得到一个类](#根据字符串得到一个类)
    + [getClass](#getClass)
    + [forName](#forName)
    + [Class属性](#Class属性)
    + [Type属性](#Type属性)
+ [获取类的成员](#获取类的成员)
    + [获取类的构造函数](#获取类的构造函数)
        + [获取类的所有构造函数](#获取类的所有构造函数)
        + [获取类的某个构造函数](#获取类的某个构造函数)
        + [调用构造函数](#调用构造函数)
    + [获取私有类函数方法并调用](#获取私有类函数方法并调用)
    + [获取静态私有类函数方法并调用](#获取静态私有类函数方法并调用)
    + [获取类的私有实例字段并修改它](#获取类的私有实例字段并修改它)
    + [获取类的静态私有字段并修改它](#获取类的静态私有字段并修改它)


反射包括以下技术
+ 根据一个字符串得到一个类的对象
+ 获取一个类所有的公用或私有、静态或实例的字段、方法、属性
+ 对泛型类的反射

## 根据字符串得到一个类

### getClass
通过一个对象，获取它的类型。

``` java
String str = "abc";
Class c1 = str.getClass();
```

### forName
通过一个字符串获取一个类型。这个字符串由类的命名空间和类的名称构成。而通过getSuperclass方法，可以获取对象的父类类型。

``` java
    try {
        Class c2 = Class.forName("java.lang.String");
        Class c3 = Class.forName("android.widget.Button");

        // 获取父类，TextView
        Class c5 = c3.getSuperclass();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
```

### Class属性
每个类都有class属性，可以得到这个类的类型

``` java
        Class c6 = String.class;
        Class c7 = java.lang.String.class;
        Class c8 = MainActivity.InnerClass.class;
        Class c9 = int.class;
        Class c10 = int[].class;
```

### Type属性
基本类型，如Boolean，都有TYPE属性，可以得到这个基本类型的类型

``` java
        Class c11 = Boolean.TYPE;
        Class c12 = Byte.TYPE;
        Class c13 = Character.TYPE;
        Class c14 = Short.TYPE;
        Class c15 = Integer.TYPE;
        Class c16 = Long.TYPE;
        Class c17 = Float.TYPE;
        Class c18 = Double.TYPE;
        Class c19 = Void.TYPE;
```


## 获取类的成员

### 获取类的构造函数
获取类的构造函数，包括public和private两种，也支持有参和无参两种类型的构造函数。

如下例子：

``` java
public class TestClassCtor {
    private String name;

    public TestClassCtor() {
        name = "test-name";
    }

    public TestClassCtor(int a) {

    }

    public TestClassCtor(int a, String b) {
        name = b;
    }

    private TestClassCtor(int a, int c) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String doSomething(String content) {
        System.out.println("TestClassCtor DoSomething: " + content);
        return "abcd";
    }

    private static void print() {
        System.out.println("TestClassCtor print()");
    }
}
```

#### 获取类的所有构造函数
``` java
        try {
            // 获取类所有构造函数，包含public/private
            Constructor[] constructors = temp.getDeclaredConstructors();
            for (Constructor c : constructors) {
                System.out.println("构造函数打印");
                int mod = c.getModifiers();
                // 输出修饰域和方法名称
                System.out.println(Modifier.toString(mod) + " " + className);

                System.out.print("构造方法参数: ");
                Class[] parameterTypes = c.getParameterTypes();
                for (Class clazz : parameterTypes) {
                    System.out.print(clazz.getName() + " ");
                }
                System.out.println("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
```

打印结果如下：

构造函数打印
private test-name
构造方法参数: int int 

构造函数打印
public test-name
构造方法参数: int java.lang.String 

构造函数打印
public test-name
构造方法参数: int 

构造函数打印
public test-name
构造方法参数: 

#### 获取类的某个构造函数
``` java
// 获取无参构造函数
Constructor constructor = temp.getDeclaredConstructor();

// 获取有参构造函数，参数类型为int
Class[] p2 = {int.class};
Constructor constructor = temp.getDeclaredConstructor(p2);

// 获取有参构造函数，参数类型为int String
Class[] p = {int.class, String.class};
Constructor constructor = temp.getDeclaredConstructor(p);
```

#### 调用构造函数

``` java
Class clazz = Class.forName("***.TestClassCtor");

// 含参
Class[] p = {int.class, String.class};
Constructor c1 = clazz.getConstructor(p);
Object o1 = c1.newInstance(1, "string_c");
System.out.println("getName: " + ((TestClassCtor) o1).getName();// 输出string_c

// 无参
Constructor c2 = clazz.getConstructor();
Object o2 = c2.newInstance();
System.out.println("getName: " + ((TestClassCtor) o2).getName());// 输出test-name
```

### 获取私有类函数方法并调用

``` java
Class clazz = Class.forName("***.TestClassCtor");
Class[] p = {int.class, String.class};
Constructor c1 = clazz.getConstructor(p);
Object o1 = c1.newInstance(1, "string_c");

// 调用private方法 doSomething
Class[] cMethod = {String.class};
// 在指定类中获取指定的方法
Method method = clazz.getDeclaredMethod("doSomething", cMethod);
method.setAccessible(true);

Object[] argList = {"test"};
Object o = method.invoke(o1, argList);
System.out.println("Result: " + o);
```

### 获取静态私有类函数方法并调用
``` java
Class clazz = Class.forName("***.TestClassCtor");
Method method=clazz.getDeclaredMethod("print");
method.setAccessible(true);
method.invoke(null);
```

### 获取类的私有实例字段并修改它
``` java
Class clazz = Class.forName("***.TestClassCtor");
Class[] p = {int.class, String.class};
Constructor c1 = clazz.getConstructor(p);
Object o1 = c1.newInstance(1, "string_c");

// 获取name字段，private
Field field = clazz.getDeclaredField("name");
field.setAccessible(true);
Object fieldObject = field.get(o1);

// 只对o1有效
field.set(o1, "name23333");
```

### 获取类的静态私有字段并修改它
``` java
Class clazz = Class.forName("***.TestClassCtor");
Class[] p = {int.class, String.class};
Constructor c1 = clazz.getConstructor(p);
Object o1 = c1.newInstance(1, "string_c");

// 获取address字段，private
Field field = clazz.getDeclaredField("address");
field.setAccessible(true);
Object fieldObject = field.get(null);

// static变量，永久有效
field.set(o1, "address_modify");
```