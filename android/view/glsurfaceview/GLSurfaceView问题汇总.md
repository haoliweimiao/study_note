# GLSurfaceView问题汇总

+ [绘制透明背景出现黑边](#绘制透明背景出现黑边)

## 绘制透明背景出现黑边

### 绘制透明背景
~~~ java
//设置透明背景
setEGLConfigChooser(8, 8, 8, 8, 16, 0);
getHolder().setFormat(PixelFormat.TRANSLUCENT);
//设置为顶层view，否则绘制的未填充的透明处会显示桌面
setZOrderOnTop(true);
~~~

### Activity Theme设置
Theme设置错误会导致出现黑色背景
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="android:windowFullscreen">true</item>
        <item name="windowNoTitle">true</item>
        <!--为了解决白屏问题-->
        <item name="android:windowDisablePreview">true</item>
        <item name="android:windowBackground">@color/transparent</item>
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowAnimationStyle">@android:style/Animation.Translucent</item>
</style>