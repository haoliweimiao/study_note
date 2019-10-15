# Application

## 方法
+	onCreate
	+	在创建应用程序时回调的方法，比任何Activity都要靠前。
+	onTerminate
	+	当终止应用程序对象时回调的方法，不保证一定被调用，当程序被内核终止以便为其它应用释放资源时将不会提醒，并且不调用应用程序对象的onTerminate方法而直接终止进程。
+	onLowMemory
	+	当后台程序已经终止且资源匮乏时会调用这个方法。好的应用程序一般会在这个方法中释放一些不必要的资源来应付当后台程序已经终止、前台应用程序内存还不够时的情况。
+	onConfigurationChanged
	+	配置改变时触发这个方法，例如手机屏幕旋转
+ 	registerActivityLifecycleCallbacks
	+	public void registerActivityLifecycleCallbacks (Application.ActivityLifecycleCallbacks callback)
	+	Add a new ComponentCallbacks to the base application of the Context, which will be called at the same times as the ComponentCallbacks methods of activities and other components are called. Note that you must be sure to use unregisterComponentCallbacks(ComponentCallbacks) when appropriate in the future; this will not be removed for you.
	+	Activity生命周期变化时就会调用ActivityLifecycleCallbacks中的方法.
+	unregisterActivityLifecycleCallbacks
	+	public void unregisterActivityLifecycleCallbacks (Application.ActivityLifecycleCallbacks callback)
