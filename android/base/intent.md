# Intent

### 内容
+ [隐式跳转](#隐式跳转)
	+ [隐式跳转-方式一](#隐式跳转-方式一)
	+ [隐式跳转-方式二](#隐式跳转-方式二)
	+ [隐式跳转-注意内容](#隐式跳转-注意内容)
+ [问题](#问题)
	+ [8.0隐式广播接收问题](#8.0隐式广播接收问题)

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



## 问题
### 8.0隐式广播接收问题

对于targetSdkVersion 在26或者以上的App，在Manifest里面注册的Receiver已经受到限制，而用Java代码动态注册的Receiver则不受影响。

targetSdkVersion 在 25或以下的App，其Receiver不受影响，即使在Android8.0以上的机器上运行。

如果targetSdkVersion 在26或者以上，在Manifest注册的Receiver可能无法接收到广播消息，并且会在Logcat里面打印出如下消息：
~~~ shell
BroadcastQueue: Background execution not allowed: 
receiving Intent { act=xxx.xxx.action flg=0x2010 (has extras) } 
to xxx.xxx.package/.broadcast.xxxReceiver
~~~


#### 有哪些影响
首先要了解一个概念。官方文档里提到implicit broadcast，可译为隐式广播，指那些没有指定接收App(即包名)的广播。
- 系统发送的广播毫无疑问都是隐式广播，因此基本上都会受到影响，除了[部分受豁免广播](https://developer.android.com/guide/components/broadcast-exceptions)之外
- App发送的自定义隐式广播，都会受到影响

#### 解决办法：
1. 如果是在一个进程中的app组件之间的通信，可以改成使用：LocalBroadcastManager

2. 如果是在多进程中的app组件之间的通信，可以改成显示广播（explicit broadcasts）

3. 如果你是在接受系统发的隐式广播，请保证你的sdk在25及以下，直到我们找到更好的解决放法。

4. 如果你是在编写发送隐式广播，你可以通过直到接收器并且发送定向的显示广播来打破这个限制。方法如下：

~~~ java
    /**
     * 发送隐式广播(解决android8.0 静态注册广播无法接收隐式广播消息)
     */
    private void sendImplicitBroadcast(Context ctxt, Intent intent) {
        PackageManager pm = ctxt.getPackageManager();
        List<ResolveInfo> matches = pm.queryBroadcastReceivers(intent, 0);

        for (ResolveInfo resolveInfo : matches) {
            Intent explicit = new Intent(intent);
            ComponentName cn = new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                    resolveInfo.activityInfo.name);
            explicit.setComponent(cn);
            ctxt.sendBroadcast(explicit);
        }
    }
~~~