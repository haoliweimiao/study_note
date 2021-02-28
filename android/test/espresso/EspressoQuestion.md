# Espresso常见问题

## click()问题

### at least 90 percent of the view’s area is displayed to the user.

#### 问题分析：
对于布局有问题的界面，这个问题非常常见，在GeneralClickAction中设置有Constraint,用来过滤错误的布局

``` java
public Matcher<View> getConstraints() {
    Matcher<View> standardConstraint = isDisplayingAtLeast(90);
    if (rollbackAction.isPresent()) {
      return allOf(standardConstraint, rollbackAction.get().getConstraints());
    } else {
      return standardConstraint;
    }
  }
```

isDisplayingAtLeast(90)这里是进行了至少显示90%的设置，但是至少在我遇到的测试中，有的界面因为确实需要覆盖实现或者修改成本大，存在这种问题。
#### 解决方案：
设置自定义ViewAction，绕过isDisplayingAtLeast(90)限制：

``` java
onView(ViewMatcher)
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isDisplayed();
                    }
                    @Override
                    public String getDescription() {
                        return null;
                    }
                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                });
```

### 单击 双击 长按 操作混淆

#### 问题分析：
这种问题在大批量运行自动换测试用例的情况下经常出现（也可能是由于我的使用问题），在多个操作连续进行时容易出现。
#### 解决方案
1.对于单击和长按，系统本身有提供方案（不过这种混淆没有遇到过）：
使用click(ViewAction)替换click()，参数为如果出现长按操作后的补偿操作。
2.对于单击和双击的混淆：
没有找到很好的官方方案，有兴趣的可以参考本文最后一个问题

## Toast 无法捕捉问题

#### 问题分析：
捕捉Toast，相信大家都知道，下面这个方法：
``` java
onView(withText(String))
    .inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView()))))
    .perform(click());
```

这个方法也确实没问题，但是请注意，在官方文档中，本方法是 用来检测多窗口的，所以如果在Dialog中弹出Toast，本方法就会有局限性，因为当前有多个窗口存在。

#### 解决方案：
这里只提供Dialog的弹出Toast的捕捉方法，可根据使用的场景，使用RootMatchers自定义修改：

``` java
onView(withText(R.string.toolsError))
                .inRoot(allOf(
                        not(isDialog()),
                        withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
```

## 无法对onData()进行doesNotExist()检查

#### 问题分析：
doesNotExist()功能的关键是济对NoMatchingViewException进行捕捉和检查，onData() 查询不到之后抛出的异常为PerformException()。
#### 解决方案：
解决方式比较粗暴：直接对onData进行PerformException异常捕捉，进行正常处理，在正常处理流畅上进行报错处理
``` java
try{
    onData(ViewMatcher)
        .perform();
    Assert.assertTrue("报错"，false);
}catch{
    //进行正常操作
}
```
***这里的检查必须和常规操作分开***

## 操作未响应问题

#### 问题分析：
这个问题出现在几百个自动化测试用例测试时，原因不明。
#### 解决方案：
因为这种问题的出现会直接导致一条测试用例崩溃，且这种情况下手动操作并不存在任何问题，这里采用了多次操作尝试的方式。
``` java
for (int i = 0; ; i++) {
    //此处为正常点击操作
    onView(withId(R.id.ibtnSurvey))
	.perform(click());
    try {
    //这里进行对应click()产生的效果进行检查
	onView(withText(R.string.ok))
	    .check(matches(isDisplay()));
    //成功将推出循环继续进行测试
	break;
    //捕捉核查项的错误
    } catch (NoMatchingViewException e) {
    //核查错误将重复进行，最多进行5次
  	if (i >= 5) {
	    Assert.assertTrue("连续五次失败", false);
  	}
	//循环直到检查到弹窗为止
    }
}
```
***同理 这种形式可以用来解决 误触和点击混淆的问题，在 try 中设置相应的操作检查，catch 中进行相应的 补偿操作（返回操作开始的状态）***