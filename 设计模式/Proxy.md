---
title:Proxy 代理模式
---

# Proxy 代理模式
**对象结构性模式**
**别名：Surrogate**


## 意图
+ 为其他对象提供一种代理以控制（隔离，使用接口）这个对象的访问。

## 动机
+ 对一个对象进行访问控制的一个原因是为了只有我们确实需要这个对象时才对它进行创建和初始化。我们可以考虑在文档中嵌入图形对象的文档编辑器。有些图形对象（如大型光栅图像）的创建开销很大。但是打开文档必须很迅速，因此我们在打开文档时应避免一次性创建所有开销很大的对象。因为并非所有这些对象在文档中都同时可见，所以也没有必要同时创建这些对象。
+ 在组件构建过程中，某些接口之间直接的依赖常常会带来很多问题、甚至更本无法实现。采用添加一层间接的接口（稳定），来隔离本来互相紧密关联的接口是一种常见的解决方法。
+ 在面向对象系统中，有些对象由于某种原因（比如对象创建开销很大，或者某些操作需要安全控制，或者需要进程外的访问等），直接访问会给使用者或者系统结构带来很多麻烦。

## 适用性
+ 在需要用比较通用和复杂的对象指针代替简单指针的时候，使用Proxy模式。厦门是一些可以使用Proxy模式常见情况：
1. **远程代理** *(Remote Proxy)* 为一个对象在不同的地址空间提供局部代表。NEXTSTEP[Add94]使用NXProxy类实现了这一目的。Coplien[Cop92]称这种代理为“大使”(Ambassador)。
2. **虚代理** *(Virtual Proxy)* 根据需要创建开销很大的对象。
3. **保护代理** *(Protection Proxy)* 控制对原始数据的访问。保护代理用于对象应该有不同的访问权限的时候。例如，在Choices操作系统[CIRM93]中KemelProxies为操作系统对象提供了访问保护。
4. **智能指引** *(Smart Reference)* 取代了简单的指针，它在访问对象时执行了一些附加操作。它的典型用途包括：
    + 对指向实际对象的引用计数，这样当该对象没有引用时，可以释放它（也称为Smart Pointers[Ede92])。
    + 当第一次引用一个持久对象时，将它装入内存。
    + 在访问一个实际对象前，检查是否已经锁定了它，以确保其他对象不能改变它。


## 参与者
+ Proxy
  + 保存一个引用使得代理可以访问实体。若RealSubject和Subject的接口相同，Proxy会引用Subject。
  + 提供一个与Subject接口相同的接口，这样代理就可以用来替代实体。
  + 控制对实体的存取，并可能负责创建和删除它。
  + 其他功能依赖于代理的类型：
      + Remote Proxy负责对请求及其参数进行编码，并像不同地址空间中的实体发送已编码的请求。
      + Virtual Proxy可以缓存实体的附加信息，以便延时对它的访问。例如使用ImageProxy缓存了图像实体的尺寸。
      + Protection Proxy检查调用者是否具有实现一个请求所必须的访问权限。
+ Subject
  + 定义RealSubject和Proxy的公用接口，这样就可以在任何使用RealSubject的地方都可以使用Proxy。
+ RealSubject
  + 定义Proxy所代表的实体。


## 协作
+ 代理根据其种类，在适当的时候向RealSubject转发请求。

---

~~~ Java
public interface SubjectImpl {
    void process();
}

public class RealSubject implements SubjectImpl {
    @Override
    public void process() {

    }
}

public class SubjectProxy implements SubjectImpl {
    @Override
    public void process() {
        //对RealSubject的一种间接访问
    }
}

public class ClientApp {
    private SubjectProxy subject;

    public ClientApp(SubjectProxy subject) {
        this.subject = subject;
    }

    public void doTask() {
        assert subject != null;

        //...
        subject.process();
        //...
    }
}
~~~