# 初识Espresso

Android自动化测试框架有很多：
Instrumentation、Robotium、Uiautomator、Appium、Selendroid、Espresso
每一种测试框架都在自己所擅长的领域有各自的特点和适用性，且都能很好的完成相应的自动化测试任务，在实际应用中可以根据自己的实际情况进行选择，在这就不多赘述了。

## Espresso

言归正题，本文主要介绍Google的开源测试框架Espresso。相对于其他框架，它更加的简洁灵活，上手难度低，对于我这种没有测试开发基础的初学者比较友好。
但是，需要注意的一点，Espresso是基于Instrumentation的，**所以不能单独进行跨app的操作。**
这种问题作为Android的一把手Google自然也考虑到了。
Google推出的另一个自动化测试框架**UIAutomator可以轻松解决跨app测试**的问题。
而且，UIAutomator 2.0发布后，Android Developers blog post所说“最重要的是，UIAutomator现在已经基于Android Instrumentation…”。因此，使用Instrumentation test runner可以运行UIAutomator和Espresso两种框架，两者可以在实际开发测试中联合使用。

### 特点

Synchronization capabilities
Each time your test invokes onView(), Espresso waits to perform the corresponding UI action or assertion until the following synchronization conditions are met:

+ The message queue is empty.
+ There are no instances of AsyncTask currently executing a task.
+ All developer-defined idling resources are idle.By performing these checks, Espresso substantially increases the likelihood that only one UI action or assertion can occur at any given time. This capability gives you more reliable and dependable test results.

在Google的官方文档介绍中，以上这段描述被放在了最显眼的位置。简单的理解：Espresso会在主线程空闲的时候，运行测试代码（不是绝对的，下文会介绍），尽可能的任意时间内只进行唯一的操作。


## 环境配置

作为Google的亲儿子，难免会对其照顾有加，相信有一些比较关心版本更新的朋友早已经知道了。在AndroidStudio2.2版本之后，在新建的项目中，AndroidStudio会默认添加Espresso的依赖。

```gradle
dependencies {
    androidTestCompile('com.android.support.test.espresso:espresso-core:3.0.1', {
        exclude group: 'com.android.support', module: 'support-annotations'
        //不导入依赖中的support-annotations，避免出现依赖冲突，会使用用户自己导入的包
    })
}
```

默认集成的Espresso包espresso-core及其相关依赖包，足以完成一般性的UI测试，除此之外Espresso还有一些扩展包，用于完成一些特殊的测试场景:

+ espresso-web :包含了对WebView测试的支持资源
+ espresso-idling-resource :Espresso与后台作业同步的机制。（已经包含在core的依赖中）
+ espresso-contrib :包含外部的贡献DatePicker， RecyclerView以及Drawer行动，交通方便检查和CountingIdlingResource。
+ espresso-intents :用于密封测试的扩展验证和存根意图。
+ espresso-remote- Espresso :多处理功能的位置。

在正式开始进行自动化测试前，需要将设备的系统动画关闭，以避免一部分因为动画切换导致的控件检索失败问题，并且可以极大的减短测试时间。
设置->开发者选项 **禁用以下设置** ：

+ 窗口动画缩放
+ 过渡动画缩放
+ 动画程序时长缩放


## 使用方式

Espresso的主要使用方法非常简单
```java
onView(ViewMatcher)
    .perform(ViewAction)
    .check(ViewAssertion);
```

可以分为三部分进行理解：

1. 控件查找：Espresso会对当前显示的界面**有序的**依次通过ViewMatcher（可以理解为查找条件）进行对比直到检测到控件符合查找条件（ViewMatcher）的控件。
2. 控件操作：针对onView提供的控件进行ViewAction操作，为了符合UI操作过程，尽量使用Espresso提供的ViewAction，**避免获取View直接对控件进行相关操作**。
3. 结果校验：对控件的当前状态进行校验。需要注意的是，这里的**校验一定是针对于某一个确定的控件**而言的。

当然仅仅的“控件三部曲”还不足以完成整个自动化测试操作，来让我们看一个完整的例子。
由于自动化测试需要依赖于app执行，在这里写一个Demo作为示例，布局比较简单就不贴代码了

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.btnTest);
        textView = (TextView)findViewById(R.id.tv_content);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTest:
                textView.setVisibility(View.VISIBLE);
                textView.setText("hello espresso!");
                break;
            default:
                break;
        }
    }
}
```

界面中默认有 button 和 textView 两个控件，其中 textView 默认为 gone，textView 中附带文本“修改内容”。当点击 button 后，textView的可视状态修改为 visible ，且 textView 中附带文本“hello espresso！”
根据界面的具体情况，我们按照如上所述进行自动化测试代码的编写。

```java
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void BeforeTest(){
        //TODO: @Before 中的操作将在 @Test 之前进行，通常进行测试前置条件的设置
    }
    @Test
    public void Test(){
        onView(withId(R.id.tv_content))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.btn01))
                .check(matches(withText("修改内容")))
                .perform(click());
        onView(withId(R.id.tv_content))
                .check(matches(withText("hello espresso!")))
                .check(matches(isDisplayed()));
    }
    @After
    public void AfterTest(){
        //TODO: @After 中的操作将在 @Test 之后进行，通常用于将测试环境回归初始状态
    }
}

```

相对于我们常规的代码编写，主要的区别就在于 @RunWith @Rule @Before @Test @After

1. @RunWith
   测试是通过Runner来执行的，这里我们的测试用例选定一个特定的AndroidJUnit4来执行。
2. @Rule
   用于设置当前测试启动的Activity
3. @Before
   它会在每个测试方法执行前都调用一次，**通常进行测试前置条件的设置**。
4. @Test
   说明该方法是测试方法。测试方法必须是public void，可以抛出异常。
5. @After
   与@Before对应，它会在每个测试方法执行完后都调用一次，**通常用于将测试环境回归初始状态**。


## 注意
1. Espresso尽量模拟用户的真实使用，选择Espresso中自带的框架函数，不要调用View的方法。
2. 尽量避免跨越Activity，每个test在同一个页面完成。
3. 尽量保证test的原子化，任何一个测试都是独立的。
4. 同一个测试类中的相邻两个@Test没有逻辑关系。
5. 初次使用某一个Espresso函数时可能会报错且 Alt+Enter 没有提示，可以写完完整一行代码后再进行尝试 Alt+Enter 操作。
6. 自动化测试失败后注意错误描述以及当前界面的内容介绍
