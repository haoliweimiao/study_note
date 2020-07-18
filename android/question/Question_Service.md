# Service Question

# Cannot BindService

``` java
Intent intent = new Intent();
intent.setAction("com.hlw.android.message");
// avoid 'Service Intent must be explicit' question
intent.setPackage(getPackageName());
// use this code to deal with service bind failed question
intent.setComponent(new ComponentName("com.hlw.android.messengerserver", 
		"com.hlw.android.messengerserver.MessengerService"));
boolean result = bindService(intent, mConn, Context.BIND_AUTO_CREATE);
Log.e(Constants.LOG_TAG, "bindService invoked ! result: " + result);
```