# 错误解决方法

+ [无法填满屏幕](#无法填满屏幕)

## 无法填满屏幕
android 5.0以上版本dialog无法填满屏幕

~~~ java
// 创建的时候指定一个主题：
AlertDialog.Builder  builder  =  new  AlertDialog.Builder(activity, R.style.MyDialogStyle);
~~~

~~~ xml
//直接修改主题的背景色就可以了（可采用随意一种色系，为了方便我采用系统自带的透明）
<stylename="MyDialogStyle"  parent="Theme.AppCompat.Dialog">
    <itemname="android:windowBackground">@android:color/transparent</item>
</style>
~~~