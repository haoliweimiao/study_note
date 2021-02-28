# Espresso之ViewAssertion

三部曲的最后一步ViewAssertion 也就是利用所给的条件对 view 的状态进行检查，核查目标 view 当前的状态是否为预计的状态。同样的 所有的断言都应该实现了 interface ViewAssertion。

``` java
public interface ViewAssertion {

  /**
   * 检查给定视图的状态（如果存在视图）；打印未匹配视图原因
   *
   * @param view 如果在匹配期间有匹配结果，则为该视图，否则为空
   * @param noViewFoundException 说明未匹配到视图的原因
   */
  void check(View view, NoMatchingViewException noViewFoundException);
}
```

## ViewAssertion

|           函数            |                           功能                            |
| :-----------------------: | :-------------------------------------------------------: |
|      doesNotExist()       |              断言目标 view 不存在于当前布局               |
|         matches()         |            断言当前 view 是否匹配指定 matcher             |
| seletedDescendantsMatch() | 目标 view 的子视图如果匹配第一个matcher，则一定匹配第二个 |

这里用的最多的时 matches(Matcher) ，可以根据自己的需求情况修改 Matcher 来变更断言。

## LayoutAssertions

|         函数         |               功能               |
| :------------------: | :------------------------------: |
|  noEllipsizedText()  | 布局不包含椭圆化或剪切的TextView |
| noMultilineButtons() | 布局中不包含具有多行文本的Button |
|      noOverlaps      |       与匹配的子视图不重叠       |

## PositionAssertions

+ isAbove(Matcher matcher)
+ isBelow(Matcher matcher)
+ isBottomAlignedWith(Matcher matcher)
+ isLeftAlignedWith(Matcher matcher)
+ isLeftOf(Matcher matcher)
+ isRightAlignedWith(Matcher matcher)
+ isRightOf(Matcher matcher)
+ isTopAlignedWith(Matcher matcher)