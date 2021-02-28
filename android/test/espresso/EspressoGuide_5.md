# Espresso之ViewAction

Espresso的官方文档中提示，尽量使用Espresso提供的操作动作，来控制view，而且Espresso也确实提供了比较全面ViewAction。
与Matcher类似，所有的操作动作都是实现了interface ViewAction。

``` java
public interface ViewAction {

    /**
     * 在 perform 使用时提及过，view 执行特定的操作需要满足特定的条件
     * 这里是针对当前 ViewAction 对 view 设置的限制条件
     */
    public Matcher<View> getConstraints();
    
     /**
     * 对当前的 view action 提供描述
     */
    public String getDescription();
    
     /**
     * 对指定的 view 进行操作
     */
    public void perform(UiController uiController, View view);
}

```

如果你看过前面的[onView()&onData()](!./../EspressoGuide_3.md)这里应该就很容易明白这里所对应的操作，这里简单的总结一下ViewAction的使用过程：

使用初始化信息拿到目标view准备开始进行操作（perform()和check()是整体的驱动器）
检查一下目标view能否胜任当前的操作getConstraints()
生成关于ViewAction的描述，用于之后和其他描述一起打印日志
正式开始在目标 view 上进行 ViewAction 操作

## ViewAction

在了解一个大概流程之后再来看自定义ViewAction并没有什么难度，这里一起看一个栗子

``` java
//获取指定view的文本内容
String tv = getText(withId(R.id.xxx));

public String getText(final Matcher<View> matcher) {
        final String[] text = {null};
        onView(matcher)
            .perform(new ViewAction() {
                 //识别所操作的对象类型
                 @Override
                 public Matcher<View> getConstraints() {
                        return isAssignableFrom(TextView.class);
                 }
                 //视图操作的一个描述
                 @Override
                 public String getDescription() {
                        return "getting text from a TextView";
                 }
                 //实际的一个操作，在之类我们就可以获取到操作的对象了。
                 @Override
                 public void perform(UiController uiController, View view) {
                      TextView textView = (TextView)view;
                       text[0] = textView.getText().toString();
                   }
              });
        return text[0];
    }
```

因为常规的view的操作已经足够使用了，这里就借用于ViewAction的特性，来获取一些相关的属性信息，使用一个数组（引用）将view中的属性获取出来，同样的方法也可以将view获取出来

### ViewActions

与Matcher相同 Espresso 为 使用者提供了大量的 ViewAction供测试使用：


|               函数                |                      功能                      |
| :-------------------------------: | :--------------------------------------------: |
|       addGlobalAssertion()        |                  设置全局断言                  |
|      actionWithAssertions()       |    包装 action ，执行前必须满足所有全局断言    |
|      removeGlobalAssertion()      |                  删除全局断言                  |
|      clearGlobalAssertions()      |                  清空全局断言                  |
|            clearText()            |                    清空文本                    |
|              click()              |                      单击                      |
| click(ViewAction rollbackAction() |             单击（防止误判为长按）             |
|        closeSoftKeyboard()        |                   关闭软键盘                   |
|           doubleClick()           |                      双击                      |
|            longClick()            |                      长按                      |
|            openLink()             |              打开连接（TextView）              |
|        openLinkWithText()         |                打开连接（Text）                |
|         openLinkWithUri()         |                打开连接（Uri）                 |
|            pressBack()            |                     返回键                     |
|      pressImeActionButton()       |                                                |
|            pressKey()             |                根据Key模拟按键                 |
|          pressMenuKey()           |                 实体键盘菜单键                 |
|           replaceText()           |                    替换文本                    |
|            scrollTo()             |                     滑动到                     |
|            swipeDown()            |                      下滑                      |
|            swipeLeft()            |                      左滑                      |
|           swipeRight()            |                      右滑                      |
|             swipeUp()             |                      上滑                      |
|            typeText()             |      获得焦点并注入文本(模拟按键单个输入)      |
|     typeTextIntoFocusedView()     | 在已获得焦点的View上注入文本(模拟按键单个输入) |