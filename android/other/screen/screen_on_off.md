---
title:安卓控制屏幕点亮与熄灭
---
# 安卓控制屏幕点亮与熄灭

需要配置以下权限

~~~ xml
<!-- 屏幕唤醒 -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
<!-- 屏幕熄灭 -->
<user-permission android:name="android.permission.DEVICE_POWER" />
~~~

### 目前问题
在Android5.1版本下，系统熄屏方法被去除？无法使用。


### 代码如下

~~~ java
package com.zkteco.android.openclosescreen;

import android.content.Context;
import android.net.Uri;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;

/**
* 屏幕亮度调节器
*/
public class ScreenManager {
    /**
    * 获得当前屏幕亮度的模式
    *
    * @return 1 为自动调节屏幕亮度,0 为手动调节屏幕亮度,-1 获取失败
    */
    public static int getScreenMode(Context context) {
        int mode = -1;
        try {
            mode = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return mode;
    }

    /**
    * 获得当前屏幕亮度值
    *
    * @return 0--255
    */
    public static int getScreenBrightness(Context context) {
        int screenBrightness = -1;
        try {
            screenBrightness = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

    /**
    * 设置当前屏幕亮度的模式
    *
    * @param mode 1 为自动调节屏幕亮度,0 为手动调节屏幕亮度
    */
    public static void setScreenMode(Context context, int mode) {
        try {
            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
            Uri uri = Settings.System
                    .getUriFor("screen_brightness_mode");
            context.getContentResolver().notifyChange(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * 保存当前的屏幕亮度值，并使之生效
    *
    * @param paramInt 0-255
    */
    public static void setScreenBrightness(Context context, int paramInt) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, paramInt);
        Uri uri = Settings.System
                .getUriFor("screen_brightness");
        context.getContentResolver().notifyChange(uri, null);
    }

    /**
    * 点亮屏幕
    */
    public static void screenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "matrix");
        if (wakeLock == null)
            return;
        if (pm.isScreenOn())
            return;
        wakeLock.acquire(100);
    }
    
    /**
    * 释放并直接灭屏
    */
    public void screenOff(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "matrix");
        wakeLock.release();
        pm.goToSleep(SystemClock.uptimeMillis());
    }
}
~~~
