# adb command

### 内容
+ [adb重启](#adb重启)
+ [全局隐藏虚拟按键](#全局隐藏虚拟按键)
+ [全局还原](#全局还原)
+ [隐藏除外指令](#隐藏除外指令)
+ [根据包名卸载app](#根据包名卸载app)
+ [打印正在运行的activity](#打印正在运行的activity)
+ [选择设备](#选择设备)
+ [上传、下拉文件](#上传、下拉文件)
+ [启动应用](#启动应用)
+ [关闭应用](#关闭应用)
+ [打印屏幕屏幕分辨率](#打印屏幕屏幕分辨率)
+ [输出安装包的APK路径](#输出安装包的APK路径)
+ [应用数据清除](#应用数据清除)
+ [打印显示活动名称](#打印显示活动名称)
+ [卸载应用](#卸载应用)
+ [发送广播](#发送广播)
+ [截屏](#截屏)
+ [录屏](#录屏)
+ [设置音量](#设置音量)

## adb重启
~~~ shell
adb kill-server
adb start-server
~~~

## 全局隐藏虚拟按键
~~~ shell
adb shell settings put global policy_control immersive.navigation=*
~~~

## 全局还原
~~~ shell
adb shell settings put global policy_control null
~~~

## 隐藏除外指令
※Sony必看 全局隐藏时进入相机“有去无回”（但可以随便拍一张照片从相册返回）
说明：以上为忽略进入相机和设置时隐藏，可通过修改程序包名自定义除外名单
~~~ shell
adb shell settings put global policy_control immersive.navigation=apps,-
com.sonyericsson.android.camera,-com.android.settings
~~~


## 根据包名卸载app
~~~ shell
adb uninstall com.paewo.dc
# com.paewo.dc 为应用的包名
~~~

## 打印正在运行的activity
~~~ shell
adb shell dumpsys activity activities
~~~

## 选择设备
~~~ shell
adb -s SH0A6PL00243 shell
~~~

## 上传、下拉文件
adb push命令 ：从电脑上传送文件到手机；
adb pull命令 ：从手机传送文件到电脑上；
~~~ shell
adb -s 192.168.212.199:5555 pull ./mnt/internal_sd/ws_log /Users/hlw/test_log
adb push /Users/hlw/Downloads/ZKDB.db /data/data/com.zkteco.android.core/databases
adb pull /data/data/com.zkteco.android.core/databases/ZKDB_not_init.db /Users/hlw/Downloads/
~~~

## 启动应用
~~~ shell
# 进入Android系统命令行
adb shell     
# am start -n ｛包(package)名｝/｛包名｝.{活动(activity)名称}
am start -n com.example.hlw.websocketdemo/com.example.hlw.websocketdemo.MainActivity  
adb shell am start -n com.zkteco.android.wieganddemo/com.zkteco.android.wieganddemo.activity.MainActivity
adb shell am start -n com.zkteco.android.checkagedemo/com.zkteco.android.checkagedemo.MainActivity

# 启动APK，在终端直接输入：
# adb shell am start -n 包名 /. 类名
# 比如要打开camera，就输入 adb shell am start -n com.android.camera/.Camera 即可。
# 关闭APK，在终端直接输入：
adb shell am force-stop 包名
# 比如要关闭camera，就输入 adb shell am force-stop com.android.camera 即可。
~~~

## 关闭应用
~~~ shell
adb shell am force-stop com.some.package
~~~

## 打印屏幕屏幕分辨率
~~~ shell
adb shell dumpsys window displays
~~~

## 输出安装包的APK路径
~~~ shell
adb shell pm path <PACKAGE>
~~~

## 应用数据清除
删除与包相关的所有数据：清除数据和缓存
~~~ shell
adb shell pm clear <PACKAGE>
~~~

## 打印显示活动名称
打印最上层的Activity名称(当前显示的Avctivity)

bat command
~~~ bat
adb shell dumpsys activity | findstr "mFocusedActivity"
~~~

shell command
~~~ shell
adb shell dumpsys activity | grep "mFocusedActivity"
~~~

## 卸载应用

~~~ shell
adb shell pm list packages -s 找到要删除的包名

# 获取包名地址
adb shell pm path com.xxx.xxx
package:/data/app/xxx/base.apk

# 挂载系统读写权限
adb remount
remount succeeded

# 删除包
adb shell rm /data/app/com.xxx.xxx-1/base.apk

# 重启后ok
adb reboot
~~~

## 发送广播

~~~ shell
adb shell am broadcast 后面的参数有：

[-a <ACTION>]
[-d <DATA_URI>]
[-t <MIME_TYPE>] 
[-c <CATEGORY> [-c <CATEGORY>] ...] 
[-e|--es <EXTRA_KEY> <EXTRA_STRING_VALUE> ...] 
[--ez <EXTRA_KEY> <EXTRA_BOOLEAN_VALUE> ...] 
[-e|--ei <EXTRA_KEY> <EXTRA_INT_VALUE> ...] 
[-n <COMPONENT>]
[-f <FLAGS>] [<URI>]

adb shell am broadcast -a com.android.test --es test_string "this is test string" --ei test_int 100 --ez test_boolean true
adb shell am broadcast -a com.zkteco.android.action_log_switch_update --ez isShowLog true
~~~

## 截屏

~~~ shell
#（保存到SDCard）
adb shell /system/bin/screencap -p /sdcard/screenshot.png
# 从SD卡导出到电脑，注意 F:\\mvp 为电脑路径，必须存在
adb pull /sdcard/screenshot.png F:\\mvp（保存到电脑）
~~~

## 录屏

~~~ shell
adb shell screenrecord /sdcard/demo.mp4
# 限制视频录制时间为10s,如果不限制,默认180s
adb shell screenrecord  --time-limit 10 /sdcard/demo.mp4
# 指定视频分辨率大小
adb shell screenrecord --size 1280*720 /sdcard/demo.mp4
# 指定视频的比特率为6Mbps,如果不指定,默认为4Mbps.
adb shell screenrecord --bit-rate 6000000 /sdcard/demo.mp4
~~~

## 设置音量

~~~ shell
media volume:  the options are as follows: 
    --stream STREAM selects the stream to control, see AudioManager.STREAM_*
                    controls AudioManager.STREAM_MUSIC if no stream is specified
    --set INDEX     sets the volume index value
    --adj DIRECTION adjusts the volume, use raise|same|lower for the direction
    --get           outputs the current volume
    --show          shows the UI during the volume change
examples:
    adb shell media volume --show --stream 3 --set 11
    adb shell media volume --stream 0 --adj lower
    adb shell media volume --stream 3 --get
~~~