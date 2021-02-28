# Espresso之onView()&onData()

## 从onView()&onData()开始

作为一个程序猿自然不能满足于三部曲，虽然不能什么都一清二楚，但是最差也要知道大概的流程吧。不然都不好意思说自己用过Espresso。所以与 Espresso 的故事就是从 Ctrl 打开 onView()开始了。
这里直接进入 Espresso 类， 这里主要有几个常用的静态工具函数

|                 函数名                  |      功能      |
| :-------------------------------------: | :------------: |
|               pressBack()               |     返回键     |
|           closeSoftKeyboard()           |   关闭软键盘   |
|  openActionBarOverflowOrOptionsMenu()   |     菜单键     |
| openContextualActionModeOverflowMenu(); | 实体键盘菜单键 |

还有几个registerIdlingResources、unregisterIdlingResources等关于IdlingResources的函数。以及本文的关键onView()和onData()，在这里分别生成了ViewInteraction和DataInteraction。

``` java
public static ViewInteraction onView(final Matcher<View> viewMatcher) {
    return BASE.plus(new ViewInteractionModule(viewMatcher)).viewInteraction();
}
```

## ViewInteraction

ViewInteraction中的公共函数非常少，只有四个：

|        函数名        |          功能          |
| :------------------: | :--------------------: |
|      perform()       |   执行ViewAction操作   |
|       check()        |   检查ViewAssertion    |
|       inRoot()       | 确定目标view所在的root |
| withFailureHandler() |    提供错误处理方式    |

### perform()

我们还是按照三部曲的顺序进行先看perform() ：

``` java
public ViewInteraction perform(final ViewAction... viewActions) {
    checkNotNull(viewActions);
    for (ViewAction action : viewActions) {
        doPerform(action);
    }
    return this;
  }
```

从方法的形参ViewAction... viewActions我们可以知道perform()是支持同时执行多个操作的，但是会通过doPerform(action)按照顺序依次执行。
到这里问题就来了，如果按照三部曲的理解来说，现在应该开始对控件执行操作了，但是需要操作的控件在哪？我们至今没有看到，难道在onView()初始化的过程中已经将View检索出来存储为成员变量了？ok，我们来看一下 ViewInteraction 有哪些成员变量：

``` java
//用于进行简单UI操作的工具
private final UiController uiController;

//用于查找View的工具
private final ViewFinder viewFinder;

//执行已提交 runnable 任务的对象
private final Executor mainThreadExecutor;

//错误处理机制与 withFailureHandler() 有关
private volatile FailureHandler failureHandler;

//view的匹配器（我们在onView（viewMatcher）传入的）
private final Matcher<View> viewMatcher;

//缺点查询 view 的 root 与 inRoot() 有关
private final AtomicReference<Matcher<Root>> rootMatcherRef;
```

好吧，现实并不是想象的那样，ViewInteraction 并没有存储 view ，里面只有用于查找 view 的工具（ViewFinder）和材料（Matcher<View>）。看来答案需要在接下来的 desugaredPerform(action)中寻找了。让我们看一下代码：

``` java
  public void perform(UiController uiController, View view) {
  //第一部分
    AdapterView<? extends Adapter> adapterView = (AdapterView<? extends Adapter>) view;
    List<AdapterViewProtocol.AdaptedData> matchedDataItems = Lists.newArrayList();
    for (AdapterViewProtocol.AdaptedData data : adapterViewProtocol.getDataInAdapterView(
        adapterView)) {
      if (dataToLoadMatcher.matches(data.getData())) {
        matchedDataItems.add(data);
      }
    }
    //第二部分
    if (matchedDataItems.size() == 0) {
      StringDescription dataMatcherDescription = new StringDescription();
      dataToLoadMatcher.describeTo(dataMatcherDescription);
      if (matchedDataItems.isEmpty()) {
        dataMatcherDescription.appendText(" contained values: ");
          dataMatcherDescription.appendValue(
              adapterViewProtocol.getDataInAdapterView(adapterView));
        throw new PerformException.Builder()
          .withActionDescription(this.getDescription())
          .withViewDescription(HumanReadables.describe(view))
          .withCause(new RuntimeException("No data found matching: " + dataMatcherDescription))
          .build();
      }
    }
    //第三部分
    synchronized (dataLock) {
      checkState(!performed, "perform called 2x!");
      performed = true;
      if (atPosition.isPresent()) {
        int matchedDataItemsSize = matchedDataItems.size() - 1;
        if (atPosition.get() > matchedDataItemsSize) {
          throw new PerformException.Builder()
            .withActionDescription(this.getDescription())
            .withViewDescription(HumanReadables.describe(view))
            .withCause(new RuntimeException(String.format(
                "There are only %d elements that matched but requested %d element.",
                matchedDataItemsSize, atPosition.get())))
            .build();
        } else {
          adaptedData = matchedDataItems.get(atPosition.get());
        }
      } else {
        if (matchedDataItems.size() != 1) {
          StringDescription dataMatcherDescription = new StringDescription();
          dataToLoadMatcher.describeTo(dataMatcherDescription);
          throw new PerformException.Builder()
            .withActionDescription(this.getDescription())
            .withViewDescription(HumanReadables.describe(view))
            .withCause(new RuntimeException("Multiple data elements " +
                "matched: " + dataMatcherDescription + ". Elements: " + matchedDataItems))
            .build();
        } else {
          adaptedData = matchedDataItems.get(0);
        }
      }
    }
    //第四部分
    int requestCount = 0;
    while (!adapterViewProtocol.isDataRenderedWithinAdapterView(adapterView, adaptedData)) {
      if (requestCount > 1) {
        if ((requestCount % 50) == 0) {
          // sometimes an adapter view will receive an event that will block its attempts to scroll.
          adapterView.invalidate();
          adapterViewProtocol.makeDataRenderedWithinAdapterView(adapterView, adaptedData);
        }
      } else {
        adapterViewProtocol.makeDataRenderedWithinAdapterView(adapterView, adaptedData);
      }
      uiController.loopMainThreadForAtLeast(100);
      requestCount++;
    }
  }
```

由于代码比较比较长，这里分为几部分来看：
第一部分：view 强制转换为 AdapterView ，取出 data 与dataToLoadMatcher()进行匹配，将所有匹配成功的结果 存储到 matchedDataItems中。
第二部分：如果matchedDataItems为空，及没有任何匹配数据，则抛出异常。
第三部分：这里会根据是否使用了atPosition()产生区别。如果使用了则会返回matchedDataItems.get(atPosition.get())类似于 List().get(atPosition)，和常规使用List一样，这里会判断是否“指针超限”。如果没有使用，就需要看matchedDataItems.size()如果正好为 0 ，可以直接返回结果，否则就会抛出Multiple data elements的异常。
第四部分：这里就是将 目标view 滑动到视图内的操作。这里有注释

``` java
// sometimes an adapter view will receive an event that will block its attempts to scroll.
```

这里不会无限制的进行尝试操作，如果超过限制会放弃本次操作。当然这不是我们想看到的，我们继续看代码makeDataRenderedWithinAdapterView()（这里就只贴关键代码了）

``` java
//第一部分
((AbsListView) adapterView).smoothScrollToPositionFromTop(position,
                adapterView.getPaddingTop(), 0);
                
    ......
                
 //第二部分  
if (adapterView instanceof AdapterViewAnimator) {
    if (adapterView instanceof AdapterViewFlipper) {
        ((AdapterViewFlipper) adapterView).stopFlipping();
    }
        ((AdapterViewAnimator) adapterView).setDisplayedChild(position);
        moved = true;
}

```

第一部分就是滑动到指定位置的主体函数，第二部分是关于动画处理的操作，这里就不介绍了（笔者还没有动画相关内容）。现在着重看第一部分。
跟踪进去，主要代码只有一句：

``` java
 AbsPositionScroller.startWithOffset();
```

继续跟踪到 AbsListView().AbsPositionScroller 类，这里已经是ListView，就不贴代码了，在这里说一下流程：
首先在startWithOffset()中会做一些判定和预处理，并计算需要滑动的参数，然后postOnAnimation(this)（因为AbsPositionScroller implement Runnable）开始运行run()在这里进行不同情况的判断正式开始滑动操作。
到此perform()介绍完毕，说了这么多总结起来就两部：1、将 目标View 移动到视图中；2、调用 onView

## check()

``` java
  public ViewInteraction check(ViewAssertion assertion) {
     AdapterDataLoaderAction adapterDataLoaderAction = load();
     return onView(makeTargetMatcher(adapterDataLoaderAction))
        .inRoot(rootMatcher)
        .check(assertion);
  }
```

直接贴出代码，相信你一定马上明白了，和perform整体操作完全一样，这里就不多加介绍了。

### inRoot()

inRoot() 就是直接调用 ViewInteraction 看一下上面 check()中的 return 就明白了。

### inAdapterView()

看一下它的使用吧，首先是在 上面提到的load()（perform()&check()调用的第一个函数）中

``` java
    onView(adapterMatcher)
      .inRoot(rootMatcher)
      .perform(adapterDataLoaderAction);
```

直接放在onView()中用来匹配 adapterView ,再就是 displayingData()中用来作为匹配 view 的保障。(详细见上文perform())

``` java
public boolean matchesSafely(View view) {

    ......
    
    if (parent != null && adapterMatcher.matches(parent)) {
             Optional<AdaptedData> data =  adapterViewProtocol.getDataRenderedByView(
                 (AdapterView<? extends Adapter>) parent, view);
             if (data.isPresent()) {
                    return adapterDataLoaderAction.getAdaptedData().opaqueToken.equals(
                              data.get().opaqueToken);
          }
    }
}
```

### atPosition()

在AdapterDataLoaderAction.perform()的第三部分中（详见上文）：
``` java
adaptedData = matchedDataItems.get(atPosition.get());
```

如果使用dataMatcher匹配的结果多于一个，则需要atPosition来进行甄别，确定唯一结果，否则报错。（可以在onData()中不筛选任何data，添加空dataMatcher，直接用atPosition(),当然，这种方法不推荐）

### usingAdapterViewProtocol()

本函数中涉及的AdapterViewProtocol是整个onData的中心，本文中使用的makeDataRenderedWithinAdapterView()
AdapterDataLoaderAction.perform()
等函数都直接或者间接的使用了默认的AdapterViewProtocol，它是Espresso访问AdapterView的中间桥梁。
常规情况下，默认的AdapterViewProtocol可以满足使用情况，不需要使用，如果您想了解推荐你看一下[Espresso的进阶: AdapterViewProtocol](https://blog.csdn.net/fei20121106/article/details/70577731)，大神写的非常详细。


## 总结

本文是笔者按照自己看代码的思路写的，整体可能会比较繁琐，也会有思考不到位的地方，请见谅，这里是对于上文提到的一些关键点的总结：

1. onData()&onView只是起到初始化的作用，真正的定位view操作实际在perform()&check中执行。
2. 不要把perform()&check定位view想的太复杂，就是将所有view排序后一个一个进行匹配尝试。所以针对线型布局中这种顺序定死的view，也可以自定义matcher排号选择第几个。
3. 每个perform()&check()都是单独进行 定位view 的操作，后续操作必须考虑上一个操作带来的影响。
4. 同一个perform()执行多个操作，和连续使用多个perform()的效果是相同的，必须注意操作的前后影响。
5. 使用withFailureHandler()自定义新的FailureHandler，根据需求增加log和屏幕截图，单纯的原始log无法满足需求。
6. 对于 AdapterView 推荐使用 onData() ,哪怕是视图可见的 view 。不然当AdapterView的数据源发生变化时，你就可以去哭了。
7. onData() 是为了适应AdapterView 而封装后的 onView()。


|          函数名          |                        功能                         |
| :----------------------: | :-------------------------------------------------: |
|       atPosition()       |    选中匹配的Adapter的第几项(出现重复匹配时使用)    |
|      inAdapterView       | 选择满足adapterMatcher的adapterview来执行onData操作 |
|          inRoot          |                   锁定指定的root                    |
|       onChildView        |     匹配”onData所匹配的item视图”中的指定子视图      |
| usingAdapterViewProtocol |        对于不符合常规的AdapterView自定义协议        |