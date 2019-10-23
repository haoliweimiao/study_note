# 关于Java的Retention元注解

### 内容
+ [Demo](#Demo)
+ [描述](#描述)
+ [验证](#验证)

## Demo
~~~ java
package com.my.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MyAnnotation {
	String value();
}
~~~

~~~ java
package com.my.test;

@MyAnnotation("This is MyAnnotation. ^_^")
class A {}

public class Test {
	public static void main(String[] args) {
		MyAnnotation annotation = A.class.getAnnotation(MyAnnotation.class);
		if (annotation != null) {
			System.out.println(annotation.value());
		} else {
			System.out.println("No such annotation.");
		}
	}
}
~~~

执行结果：

~~~ shell
No such annotation.
~~~

如果把MyAnnotation类的Retention改为RetentionPolicy.CLASS，结果还是No such annotation。

如果改为RetentionPolicy.RUNTIME，此时的结果为：

~~~ shell
This is MyAnnotation. ^_^
~~~

## 描述

在JavaDoc中，RetentionPolicy是这样描述的：

| RetentionPolicy	| 说明	| 举例| 
| :-:| :-:| :-:| 
| SOURCE	| 注解只保留在代码中，class文件中没有	| @Override, @SupressWarnings | 
| CLASS	    | 注解会保留在class文件中，但运行时不能取得	 | | 
| RUNTIME	| 注解会保留在class文件中，而且运行时可以取得	| @Deprecated | 

可以理解的是，@Override注解只是为了编译器能检查是否当前的方法真的是在复写父类的方法，而@SuppressWarnings也只是为了抑制代码中的警告而已，在代码编译之后并没有作用，所以不需要写在CLASS文件中，于是，这类注解的RetentionPolicy自然应该为SOURCE。

## 验证

为了验证以上说明，接下来，我们使用javap工具把标注有MyAnnotation注解的类A的class文件进行反编译。

RetentionPolicy.SOURCE

~~~ shell
javap -v A.class
Classfile /f:/work/workspace/java/AnnotationDemo/bin/app/A.class
  Last modified 2019-10-23; size 238 bytes
  MD5 checksum d3d254929587d2f7635e40969a403f8a
  Compiled from "App.java"
class app.A
  minor version: 0
  major version: 52
  flags: ACC_SUPER
Constant pool:
   #1 = Class              #2             // app/A
   #2 = Utf8               app/A
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = Utf8               Code
   #8 = Methodref          #3.#9          // java/lang/Object."<init>":()V
   #9 = NameAndType        #5:#6          // "<init>":()V
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lapp/A;
  #14 = Utf8               SourceFile
  #15 = Utf8               App.java
{
  app.A();
    descriptor: ()V
    flags:
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #8                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 4: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lapp/A;
}
SourceFile: "App.java"
~~~

RetentionPolicy.CLASS

~~~ shell
javap -v A.class
Classfile /f:/work/workspace/java/AnnotationDemo/bin/app/A.class
  Last modified 2019-10-23; size 342 bytes
  MD5 checksum 38fd2df86663deb09a05b333a3ece780
  Compiled from "App.java"
class app.A
  minor version: 0
  major version: 52
  flags: ACC_SUPER
Constant pool:
   #1 = Class              #2             // app/A
   #2 = Utf8               app/A
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = Utf8               Code
   #8 = Methodref          #3.#9          // java/lang/Object."<init>":()V
   #9 = NameAndType        #5:#6          // "<init>":()V
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lapp/A;
  #14 = Utf8               SourceFile
  #15 = Utf8               App.java
  #16 = Utf8               RuntimeInvisibleAnnotations
  #17 = Utf8               Lapp/MyAnnotation;
  #18 = Utf8               value
  #19 = Utf8               This is MyAnnotation. ^_^
{
  app.A();
    descriptor: ()V
    flags:
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #8                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 4: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lapp/A;
}
SourceFile: "App.java"
RuntimeInvisibleAnnotations:
  0: #17(#18=s#19)
~~~

可以看到：

+ 中间包含了RuntimeInvisibleAnnotations这一部分，表明注解确实已经写入到class文件中，而且是运行时不可见的(RuntimeInvisibleAnnotations)
+ 而在常量池中，可以清楚的看到自定义注解的权限定类名，注解的属性，和属性的值均被写入到class中，而且是常量

RetentionPolicy.RUNTIME

~~~ shell
javap -v A.class
Classfile /f:/work/workspace/java/AnnotationDemo/bin/app/A.class
  Last modified 2019-10-23; size 340 bytes
  MD5 checksum afd577679bfcad043c46d1b04c3fce1b
  Compiled from "App.java"
class app.A
  minor version: 0
  major version: 52
  flags: ACC_SUPER
Constant pool:
   #1 = Class              #2             // app/A
   #2 = Utf8               app/A
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = Utf8               Code
   #8 = Methodref          #3.#9          // java/lang/Object."<init>":()V
   #9 = NameAndType        #5:#6          // "<init>":()V
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lapp/A;
  #14 = Utf8               SourceFile
  #15 = Utf8               App.java
  #16 = Utf8               RuntimeVisibleAnnotations
  #17 = Utf8               Lapp/MyAnnotation;
  #18 = Utf8               value
  #19 = Utf8               This is MyAnnotation. ^_^
{
  app.A();
    descriptor: ()V
    flags:
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #8                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 4: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lapp/A;
}
SourceFile: "App.java"
RuntimeVisibleAnnotations:
  0: #17(#18=s#19)
~~~

可以看到：

+ 和CLASS一样，中间也包含了RuntimeInvisibleAnnotations这一部分，但注解是运行时可见的(RuntimeVisibleAnnotations)
+ 和CLASS一样，注解的权限定类名，注解的属性，和属性的值同样被写入到class的常量池中

到这里可以得出以下结论：

+ 一个带注解的类在编译后，如果注解的RetentionPolicy是CLASS或RUNTIME，那么此注解的信息会被记录到类的量池中
+ 如果要在代码中获取注解，需要把Retention设置为RetentionPolicy.RUNTIME