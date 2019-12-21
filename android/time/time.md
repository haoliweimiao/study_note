# Time

+ [禁用时间同步](#禁用时间同步)

## 禁用时间同步
Disable Automatic Date & Time


~~~ java
// Link for API 17 and above
android.provider.Settings.Global.getInt(
		getContentResolver(), 
		android.provider.Settings.Global.AUTO_TIME, 0);
// Link for API 16 and below
android.provider.Settings.System.getInt(
		getContentResolver(), 
		android.provider.Settings.System.AUTO_TIME, 0);
~~~