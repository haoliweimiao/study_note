# TextView 不换行问题

## 出现情况

~~~ java
View view = LayoutInflater.from(context).
                    inflate(R.layout.layout_print_content, null, false);
LinearLayout llContent = view.findViewById(R.id.ll_content);

TextView tv = new TextView(mContext);
//......TextView初始化设置

llContent.addView(tv);
~~~

TextView 文字出现在同一行，不进行换行

## 网络解决方法

+ setInputType (InputType.TYPE_TEXT_FLAG_MULTI_LINE);
+ https://stackoverflow.com/questions/35223193/textview-doesnt-wrap-text
+ https://github.com/ea167/android-layout-wrap-content-updater
+ ~~~
    android:inputType="textMultiLine"
    android:maxLength="200"
    android:maxLines="5"
    android:scrollHorizontally="false"
    android:scrollbars="vertical" 
  ~~~

## 解决方法
+ 计算文本长度，接近设定的最大宽度时，自动换行，使用下一个TextView装载文本(整体采用LinearLayout装载)