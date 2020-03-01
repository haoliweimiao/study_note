# Intent

### 内容
+ [隐式跳转](#隐式跳转)
	+ [隐式跳转-方式一](#隐式跳转-方式一)
	+ [隐式跳转-方式二](#隐式跳转-方式二)
	+ [隐式跳转-注意内容](#隐式跳转-注意内容)

## 隐式跳转

### 隐式跳转-方式一
AndroidManifest.xml
~~~ xml
<activity android:name=".SettingActivity">
	<intent-filter>
		<action android:name="material.com.settings"/>
		<category android:name="android.intent.category.DEFAULT"/>
	</intent-filter>
</activity>
~~~

~~~ java
Intent intent = new Intent("material.com.settings");
startActivity(intent);
~~~

### 隐式跳转-方式二
包名+类名
~~~ java
Intent intent = new Intent();
intent.setClassName("App包名","Activity路径");
intent.setComponent(new ComponentName("App包名","Activity路径"));
startActivity(intent);
~~~

### 隐式跳转-注意内容
+	隐式跳转的Activity不存在(被移除等状况)，跳转会出现奔溃。使用隐式跳转需要验证是否会接受Intent，需要对Intent对象调用 resolveActivity()，如果结果非空，则至少有一个应用能够处理该Intent，且可以安全的调用startActivity();如果结果为空，则不该使用该Intent。
	+	~~~ java
		if(intent.resolveActivity(getPackageManager()) != null){
			startActivity(intent);
		}
		~~~
+	出于对安全问题的考虑，其他App也可以通过隐式的Intent来启动Activity。确保只有自己的App能够启动组件，需要设置 exported=false，其他App将无法跳转到我们的App中。