# Espresso进阶

## onData
<font color=red>onView()</font>方法采用hamcrest适配器，用于匹配当前视图结构中有且仅有一个的控件。在大多数情况下<font color=red>onView()</font>可以满足我们在测试过程中对于控件定位的需求。但是，在处理<font color=red>AdapterView</font>的时候，由于<font color=red>AdapterView</font>的数据源可能很长，很多时候无法一次性将所有数据源显示在屏幕上，对于没有显示在屏幕上的那部分数据，我们通过<font color=red>onView()</font>是没有办法找到的。

常见的ListView、GridView、Spinner都属于AdapterView。
在使用方法上onData()和onView()并没有过大的差异，主要的区别是：

+ onView()匹配当前视图结构中有且仅有一个的控件
+ onData()匹配AdapterView()数据源中有且仅有一个的数据


## 示例
来看一下Espresso的官方文档中例子，界面很简单Activity只有一个ListView，而且根据官方的描述可以知道，当前ListView的每一行都是对应一个Map

Item 0  7
Item 1  7
Item 2  7
Item 3  7
...
Item 9  7
Item 10  8
Item 11  8
Item 23  8

### 使用onView()
首先用onView()进行控件查找操作，由于onView()只能对视图内的控件进行查找，这里选择第一条进行测试：

``` java
onView(allOf(withText("7"), hasSibling(withText("item 0"))))
    .perform(click());
```

这里想要进行点击操作的是第一个item中的“7”，所以使用匹配器withText("7")进行筛选，但是由于当前界面内有多个控件中text为“7”，所以额外添加匹配器hasSibling(withText("item 0"))选择相邻控件中包含text“7”的控件，由于这里使用了多个匹配器便增加了allOf()来将多个匹配器整合起来，当做一条完整的匹配条件使用。

### 使用onData()
现在用onData()进行控件查找操作，为了彰显onData()与上面的区别，这里选择第五十条进行测试：

``` java
onData(allOf( 
  is(instanceOf(Map.class)),
  hasEntry(equalTo("STR"), is("item: 50")))          
  .perform(click());
```

allOf()的用法相同，用来将多个匹配条件整合成一条。
instanceOf(Map.class)就是字面意思，匹配的数据继承自Map.class。
hasEntry(equalTo("STR"), is("item: 50) ))可以简单的理解为查找Map<"STR","item:50">，其中is()和equalTo()是将string封装为Matcher的方式，会在之后的文章中进行介绍，这里就不过多的赘述了。

还有一点，因为onData()是对数据进行匹配的，所以当在同一个界面中有多个AdapterView时，就会报错，因为电脑不清楚你需要对哪一组数据进行操作，这里可以使用inAdapterView()来确定当前需要的数据源。

``` java
onData(Matcher<object>)
    .inAdapterView(Matcher<View>)
    .perform(click());
```

***注意：
onData()不适用于RecyleView和ScrollView，这两者与AdapterView在表现形式上类似，但是工作原理是不同的，不能混淆。***

+ ScrollView
  
    如果操作的视图在 ScrollView （水平或垂直方向）中，需要考虑在对该视图执行操作（如 click() ）之前通过 scrollTo() 方法使其处于显示状态。这样就保证了视图在执行其他操作之前是在当前视图范围内。
    onView(…).perform(scrollTo(), click());

+ RecyleView
  
    如果想要与RecyleView交互进行自动化测试，需要引入“espresso-contrib”，里边包含一系列的Actions可以用于滚动和点击。

``` java
/**
 *点击position位置的item
 */
onView(withId(R.id.recycleview))
 .perform(RecyclerViewActions.actionOnItemAtPosition(position,click()));
    
/**
 *滑动到position位置的item
 */
onView(withId(R.id.recycleview))
    .perform(scrollToPosition(position));
```

## Toast
Toast并没有在我们的常规视图中，Android支持多窗口，如果我们使用常规的方式是无法检测到Toast的，所以这里需要使用inRoot()来进行对Toast的匹配：
``` java
onView(withText("South China Sea"))
    .inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView()))))
    .perform(click());
```

## IdlingResource
IdlingResource本身只是一个简单的接口
``` java
public interface IdlingResource {
    //标识异步资源占用的名字，在日志中会显示
    public String getName();
    //返回目前资源是否可用(闲置)，
    public boolean isIdleNow();
    //Espresso会注册此回调
    public void registerIdleTransitionCallback(ResourceCallback callback);
    //回调接口
    public interface ResourceCallback {
        public void onTransitionToIdle();
    }
}
```

### 使用方法
1. 使用时首先实现接口IdlingResource对public boolean isIdleNow();的返回值进行控制
``` java
public class SimpleIdlingResource implements IdlingResource {

    @Nullable private volatile ResourceCallback mCallback;
    /**
     * 管理阻塞状态
     */
     private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    /**
     * 在耗时操作的前后分别调用此方法，来改变 isIdleNow() 的状态，阻塞测试
     */
    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
```

2. Activity中在耗时操作开始和结束时分别修改Idle状态。
``` java
private void loadData() {
    //耗时操作开始变更为忙碌状态（false）阻塞测试线程
    mIdlingResource.setIdleState(false);
    
    loadData(new Callback{
        @Override
        void onCall(Data data){
            content.setText("finish data");

            //耗时操作完成变更为空闲状态（true）开启测试线程
            mIdlingResource.setIdleState(true);
     }
})
```

3. 在Test的@Before和@After中分别进行注册和注销操作：
``` java
Espresso.registerIdlingResources(mIdlingResource);

Espresso.unregisterIdlingResources(mIdlingResource);
```

注意：
Activity中与Test中为相同mIdlingResource，需要在Test@Before中获取Activity中的mIdlingResource，IdlingResource对象需要在Activity（业务逻辑代码）中创建

IdlingResource的使用，会涉及到App中逻辑代码的变化，为了测试专门在业务代码中增加额外的逻辑，需要测试人员对于代码或者开发人员对于测试有一定的了解，亦或者两者不分，才能真正良好的IdlingResource。

## ActionBar
ActionBar有两种不同的形式，普通的ActionBar直接通过onView(withId(R.id.xxx))访问即可。
另一种就是菜单中的条目，常规的菜单条目可以通过以下代码执行：
openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
在带有硬件悬浮菜单按钮的设备上，可以通过通过下面的方式模拟硬件点击：
（没有遇到过这种设备，所以也没有使用过）
openContextualActionModeOverflowMenu();

## Espresso-Intent
当使用 Espresso-Intents 时，应当用IntentsTestRule替换ActivityTestRule。 IntentsTestRule使得在 UI 功能测试中使用 Espresso-Intents API 变得简单。该类是 ActivityTestRule 的扩展，它会在每一个被@Test注解的测试执行前初始化 Espresso-Intents，然后在测试执行完后释放Espresso-Intents。
Espresso-Intents分别提供了Intent validation和Intent stubbing的方法intended() 和intending().可以理解为intended()是在Intent发出后进行检查 ，而intending()是在Intend发出之前设置检查项。这里举出官方Demo的栗子：

``` java
@Test
public void validateIntentSentToPackage() {
    // 用户发出了“打电话”的 Intent
    user.clickOnView(system.getView(R.id.callButton));
    //检查 Intent 已经被发出
    intended(toPackage("com.android.phone"));
}


@Test
public void activityResult_IsHandledProperly() {
    /**
     * 新建 Intent 并封装为 ActivityResult
     */
    Intent resultData = new Intent();
    String phoneNumber = "123-345-6789";
    resultData.putExtra("phone", phoneNumber);
    ActivityResult result = new ActivityResult(Activity.RESULT_OK, resultData);
    
    //设置对Intent的检查
    intending(toPackage("com.android.contacts")).respondWith(result));
    
    //执行发送Intent的操作
    onView(withId(R.id.pickButton)).perform(click());
    onView(withId(R.id.phoneNumber).check(matches(withText(phoneNumber)));
}
```