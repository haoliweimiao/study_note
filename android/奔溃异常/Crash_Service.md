# Crash About Service

+ [Service Intent must be explicit](#Service Intent must be explicit)

## Service Intent must be explicit

方法1
``` java 
Intent mIntent = new Intent();
//自定义的Service的action
mIntent.setAction("XXX.XXX.XXX");
//自定义Service的包名(!!!添加该句)
mIntent.setPackage(getPackageName());
context.startService(mIntent);
```

方法 2

``` java
    /*** 
	 * 1. 先通过一个函数将隐式调用转变为显示调用
     * Android L (lollipop, API 21) introduced a new problem when trying to invoke implicit intent, 
     * "java.lang.IllegalArgumentException: Service Intent must be explicit" 
     * 
     * If you are using an implicit intent, and know only 1 target would answer this intent, 
     * This method will help you turn the implicit intent into the explicit form. 
     * 
     * Inspired from SO answer: http://stackoverflow.com/a/26318757/1446466 
     * @param context 
     * @param implicitIntent - The original implicit intent 
     * @return Explicit Intent created from the implicit original intent 
     */  
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {  
        // Retrieve all services that can match the given intent  
        PackageManager pm = context.getPackageManager();  
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);  

        // Make sure only one match was found  
        if (resolveInfo == null || resolveInfo.size() != 1) {  
            return null;  
        }  

        // Get component info and create ComponentName  
        ResolveInfo serviceInfo = resolveInfo.get(0);  
        String packageName = serviceInfo.serviceInfo.packageName;  
        String className = serviceInfo.serviceInfo.name;  
        ComponentName component = new ComponentName(packageName, className);  

        // Create a new intent. Use the old one for extras and such reuse  
        Intent explicitIntent = new Intent(implicitIntent);  

        // Set the component to be explicit  
        explicitIntent.setComponent(component);  

        return explicitIntent;  
    }  

	/**
	 * 调用方法
	 */
	private void test(){
		Intent intent = new Intent();
        intent.setAction(ACTION_BIND_SERVICE);
        final Intent eintent = new Intent(createExplicitFromImplicitIntent(this,intent));
        bindService(eintent,mServiceConnection, Service.BIND_AUTO_CREATE);
	}

```

