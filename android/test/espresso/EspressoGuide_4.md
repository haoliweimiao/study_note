# Espresso之Matcher

只要使用了Espresso，那么你一定不会对withId(R.id.xxx)和withText(R.string.xxx)这些ViewMatchers感到陌生。实际上无论是ViewMatchers、RootMatchers亦或者是Matchers，这些本质上都是Matcher。只是为了方便不同的使用环境进行了封装，本文将对Matcher以及这些封装后的工具进行分析。

## Matcher

所有的matcher都实现了接口Matcher

``` java
public interface Matcher<T> extends SelfDescribing {

    //遍历当前视图中的 view ，匹配则返回true，失败返回false
	boolean matches(Object item);
    
    //生成本次匹配操作的描述信息
	void describeMismatch(Object item, Description mismatchDescription);
}
```

其中matches()是匹配的重点所在，在这里执行匹配的操作。
但是在使用过程中，我们并没有直接实现Matcher，而是实现其子类BaseMatcher所派生的封装类：TypeSafeMatcher和BoundedMatcher


### TypeSafeMatcher

TypeSafeMatcher封装后的最大的区别是，增加了 isInstance()检查当前参加匹配的目标是否符合条件。我们看一下源码，为了便于区别和阅读，只筛选出来一部分代码：

``` java
public abstract class TypeSafeMatcher<T> extends BaseMatcher<T> {
    final private Class<?> expectedType;
    protected TypeSafeMatcher(Class<?> expectedType) {
        this.expectedType = expectedType;
    }
    protected abstract boolean matchesSafely(T item);
    public final boolean matches(Object item) {
        return item != null
                && expectedType.isInstance(item)
                && matchesSafely((T) item);
    }
    final public void describeMismatch(Object item, Description description) {
	......
    }
}

```

这里我们可以清楚的看出和原始的BaseMatcher最大的区别是存储了一个expectedType,在matchesSafely()判断前会增加一个判断，当前 item是否是expectedType的实例或者子类。这样可以防止出现不同类型的恰好匹配的情况，相对于BaseMatcher是更加安全的。
我们看一个具体使用的栗子：

``` java
  public static Matcher<View> withId(final int id) {
    return new TypeSafeMatcher<View>() {
      ......
      @Override
      public void describeTo(Description description) {
        ......
      }
      @Override
      public boolean matchesSafely(View view) {
        ......
        return id == view.getId();
      }
    };
  }
```

这是我们常用的onView(withId(R.id.xxx))，看过前一篇文章我们应该知道onView()这里需要的参数为Matcher，所以我们一般使用时，会采用上面的方式书写直接使用泛型。
代码逻辑很简单，就是依次获取view的id，匹配我们输入的 R.id.xxx，成功返回true，失败返回false。
在这里泛型是用于onView(),如果用于onData则需要根据 data 的类型类设置泛型。

### BoundedMatcher

上文已经说到TypeSafeMatcher是在BaseMatcher的基础上增加了类型的甄别，这里BoundedMatcher就是在TypeSafeMatcher的基础上在增加了一层安全保障。我们看一下源码，为了便于区别和阅读，只筛选出来一部分代码：

``` java
public abstract class BoundedMatcher<T, S extends T> extends BaseMatcher<T> {

  private final Class<?> expectedType;
  private final Class<?>[] interfaceTypes;

  public BoundedMatcher(Class<?> expectedType, Class<?> interfaceType1,
      Class<?>... otherInterfaces) {
    //将形参类型分别保存在expectedType和interfaceTypes中
    ......
    }
  }

  protected abstract boolean matchesSafely(S item);
  
  public final boolean matches(Object item) {
    if (item == null) {
      return false;
    }

    if (expectedType.isInstance(item)) {
      for (Class<?> intfType : interfaceTypes) {
        if (!intfType.isInstance(item)) {
          return false;
        }
      }
      return matchesSafely((S) item);
    }
    return false;
  }
}
```

原理上和TypeSafeMatcher时没有任何区别的，只是条件上更加的严苛。这样做还有其他的便捷之处，下面我们看个栗子：

``` java
  public static Matcher<View> withText(final Matcher<String> stringMatcher) {
    ......
    return new BoundedMatcher<View, TextView>(TextView.class) {
      @Override
      public void describeTo(Description description) {
        ......
      }
      @Override
      public boolean matchesSafely(TextView textView) {
        return stringMatcher.matches(textView.getText().toString());
      }
    };
  }
```

这里直接对view的类型增加了限制，所以在匹配实现中不需要关注类型项，只关注 对象的具体业务。

***注意：TypeSafeMatcher和BoundedMatcher使用时不要重写matches，matches重写后类别将失去本来的意义，如果需要重写，请直接继承BaseMatcher。***


## ViewMatchers&RootMatchers&Matchers

这里会将这几部分的静态函数陈列出来，看一遍有一些印象，在之后的使用过程中可以提高工作效率，至少不用有什么想法都自己去自定义，可能已经有现成的轮子在那，况且自己做的轮子还不一定是圆的。(一下均为个人理解，如有错误请指正)

### ViewMatchers

|           函数            |                     功能                      |
| :-----------------------: | :-------------------------------------------: |
|       assertThat()        |            用于生成断言描述的工具             |
|  hasContentDescription()  |            匹配具有内容描述的view             |
|      hasDescendant()      |    匹配具有特定子视图（直接或间接）的view     |
|      hasErrorText()       |      匹配getError为特定字符串的EditView       |
|        hasFocus()         |              匹配获取焦点的view               |
|      hasImeAction()       |   匹配支持输入，并且具有特定IMEAction的view   |
|        hasLinks()         |           匹配具有超链接的TextView            |
|       hasSibling()        |          匹配具有特定相邻view的view           |
|    isAssignableFrom()     |            匹配继承自特定类的view             |
|        isChecked()        |  匹配实现Checkable接口并且处于选中状态的View  |
|       isClickable()       |              匹配可以点击的view               |
|  isCompletelyDisplayed()  |          匹配全部显示在视图中的view           |
|     isDescendantOfA()     |    匹配具有特定父视图（直接或间接）的view     |
|       isDisplayed()       |      匹配显示在视图中（包括部分）的view       |
|   isDisplayingAtLeast()   |      匹配显示在视图中超过指定比值的view       |
|        isEnabled()        |         匹配当前可用（非灰色）的view          |
|       isFocusable()       |            匹配可以获取焦点的view             |
|   isJavascriptEnabled()   |              匹配开启JS的webView              |
|      isNotChecked()       | 匹配实现Checkable接口并且处于未选中状态的View |
|         isRoot()          |             匹配本身为root的view              |
|       isSelected()        |               匹配被选中的view                |
|  supportsInputMethods()   |              匹配支持输入的View               |
|        withChild()        |         匹配具有特定直接子视图的view          |
|      withClassName()      |            匹配具有特定类名的view             |
| withContentDescription()  |          匹配具有特定内容描述的view           |
| withEffectiveVisibility() | 匹配显示在屏幕上（所有父视图为Visible）的view |
|        withHint()         |       匹配getHint为指定字符串的TextView       |
|         withId()          |             匹配具有指定id的view              |
|      withInputType()      |        匹配具有指定输入类型的EditView         |
|       withParent()        |         匹配具有特定直接父视图的view          |
|    withResourceName()     |          匹配具有指定资源名称的view           |
|     withSpinnerText()     |      匹配getSeletedItem为指定文本的view       |
|       withTagKey()        |           匹配getTag为指定值的view            |
|        withText()         |       匹配getText为指定字符串的TextView       |

## RootMatchers
|       函数        |          功能          |
| :---------------: | :--------------------: |
|    isDialog()     |   匹配是对话框的root   |
|   isFocusable()   |   匹配拥有焦点的root   |
| isPlatformPopup() |   匹配是弹出窗的root   |
|   isTouchable()   |   匹配可以触摸的root   |
|  withDecorView()  | 匹配满足特定条件的root |


## Matchers

|            函数             |                                                                     功能                                                                     |
| :-------------------------: | :------------------------------------------------------------------------------------------------------------------------------------------: |
|           allOf()           |                                            将所有matcher合并为一个matcher（必须满足所有matcher）                                             |
|            any()            |                                                生成一个判定是否为指定类实例或者子类的matcher                                                 |
|           anyOf()           |                                          将所有matcher合并为一个matcher（满足至少一个matcher即可）                                           |
|         anything()          |                                           生成一个匹配任何对象的matcher（matches写死返回值为true）                                           |
|           array()           |                                由n个matcher生成一个可以对应匹配array[n]，中每个data的matcher（必须依次对应）                                 |
|      arrayContaining()      |                                      由n个data生成一个可以对应匹配array[n]，中每个data的matcher（必须依                                      | 次对应）                |
| arrayContainingInAnyOrder() |                                             由n个data生成一个可以对应匹配array[n]，中每个data的                                              | matcher（不必依次对应） |
|       arrayWithSize()       |                                                      生成匹配指定array.size()的matcher                                                       |
|           both()            |                                                        将两个matcher合并成一个matcher                                                        |
|          closeTo()          |                                       生成matcher匹配误差范围内的数：num∈[operand-error,operand+error]                                       |
|      comparesEqualTo()      |                                                           生成matcher匹配指定value                                                           |
|         contains()          |                                               iterable中每一项符合对应matcher（必须依次匹配）                                                |
|    containsInAnyOrder()     |                                               iterable中每一项符合对应matcher（不必依次匹配）                                                |
|      containsString()       |                                                                包含特定string                                                                |
|        describedAs()        |                                                              更改matcher的描述                                                               |
|          either()           |                                                        指定对象与指定匹配器匹配时匹配                                                        |
|           empty()           |                                                                collection为空                                                                |
|        emptyArray()         |                                                                   数组为空                                                                   |
|     emptyCollectionOf()     |                                                                collection为空                                                                |
|       emptyIterable()       |                                                                 Iterable为空                                                                 |
|      emptyIterableOf()      |                                                                 Iterable为空                                                                 |
|         endsWith()          |                                                            String以指定字符串结尾                                                            |
|          equalTo()          |                                                                 封装equalTo                                                                  |
|    equalToIgnoringCase()    |                                                          string.equalTo()忽略大小写                                                          |
| equalToIgnoringWhiteSpace() |                                                       string.equalTo()忽略大小写和留白                                                       |
|         eventFrom()         |                                                     匹配从指定source中派生的eventObject                                                      |
|         everyItem()         |                                                     Iterable中任何一项都符合目标matcher                                                      |
|        greaterThan()        |                                                                  大于指定值                                                                  |
|   greaterThanOrEqualTo()    |                                                                大于等于指定值                                                                |
|         hasEntry()          |                                                              匹配指定Map<K , V>                                                              |
|          hasItem()          |                                                          匹配具有指定item的Iterable                                                          |
|      hasItemInArray()       |                                                            匹配具有指定item的数组                                                            |
|         hasItems()          |                                                        匹配具有指定多个item的Iterable                                                        |
|          hasKey()           |                                                               具有特定K 的Map                                                                |
|        hasProperty()        |                                                          具有指定名称成员变量的对象                                                          |
|          hasSize()          |                                                             Collection为指定size                                                             |
|        hasToString()        |                                                          匹配toString为指定值的对象                                                          |
|         hasValue()          |                                                                具有特定V的Map                                                                |
|         hasXPath()          | Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node contains a node at the specified xPath, with any content. |
|        instanceOf()         |                                                          为特定class的实例或者子类                                                           |
|            is()             |                                                           封装上文matcher：equalTo                                                           |
|            isA()            |                                                         封装上文matcher：instanceOf                                                          |
|   isEmptyOrNullStringv()    |                                                                ""或者空String                                                                |
|       isEmptyString()       |                                                                  “”(String)                                                                  |
|           isIn()            |                                                            匹配指定Array中的item                                                             |
|          isOneOf()          |                                                               匹配列举中的一项                                                               |
|     iterableWithSize()      |                                                            iterable的size为指定值                                                            |
|         lessThan()          |                                                                  小于特定值                                                                  |
|     lessThanOrEqualTo()     |                                                                小于等于特定值                                                                |
|            not()            |                                                              不匹配指定matcher                                                               |
|       notNullValue()        |                                                                   不为空值                                                                   |
|         nullValue()         |                                                                     空值                                                                     |
|       sameInstance()        |                                                                  对象相同的                                                                  |
|   samePropertyValuesAs()    |                                                                具有相同属性值                                                                |
|        startsWith()         |                                                            String以特定字符串开始                                                            |
|   stringContainsInOrder()   |                                                          具有特定一个字符串的String                                                          |
|        theInstance()        |                                                       对象相同的与上文sameInstance相同                                                       |
|    typeCompatibleWith()     |                                                          当前class是继承自指定class                                                          |