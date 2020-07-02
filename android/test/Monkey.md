# Monkey

Monkey命令介绍
monkey命令格式：adb shell monkey [options] <event-count>；其中options值有很多选项，可以在Monkey官网查看，也可以输入命令 adb shell monkey -help 可以查看到其对应的所有值（方便使用，需要将adb添加到环境变量）

-p 用于约束限制，用此参数指定一个包，指定包后Monkey将被允许启动指定应用；如果不指定包，  Monkey将被允许随机启动设备中的应用（主Activity有android.intent.category.LAUNCHER 或android.intent.category.MONKEY类别 ）。比如 adb shell monkey -p xxx.xxx.xxx 1  ; xxx.xxx.xxx 表示应用包名，1 表示monkey模拟用户随机事件参数，最低1，这样就能把应用启动起来
-c 指定Activity的category类别，如果不指定，默认是CATEGORY_LAUNCHER 或者 Intent.CATEGORY_MONKEY；不太常用的一个参数
-v 用于指定反馈信息级别，也就是日志的详细程度，分Level1、Level2、Level3；-v 默认值，仅提供启动提示，操作结果等少量信息 ，也就是Level1，比如adb shell monkey -p  xxx.xxx.xxx -v 1 ；-v -v 提供比较详细信息，比如启动的每个activity信息 ，也就是Level2，比如adb shell monkey -p xxx.xxx.xxx -v -v 1 ；-v -v -v 提供最详细的信息 ，比如adb shell monkey -p xxx.xxx.xxx -v -v -v 1 
-s 伪随机数生成器的种子值，如果我们两次monkey测试事件使用相同的种子值，会产生相同的事件序列；如果不指定种子值，系统会产生一个随机值。种子值对我们复现bug很重要。使用如下adb shell monkey -p xxx.xxx.xxx -s 11111 10；这也是伪随机事件的原因，因为这些事件可以通过种子值进行复现
--ignore-crashes 忽略异常崩溃，如果不指定，那么在monkey测试的时候，应用发生崩溃时就会停止运行；如果加上了这个参数，monkey就会运行到指定事件数才停止。比如adb shell monkey -p xxx.xxx.xxx -v -v -v  --ignore-crashes 10 
--ignore-timeouts 忽略ANR，情况与4类似，当发送ANR时候，让monkey继续运行。比如adb shell monkey -p xxx.xxx.xxx -v -v -v  --ignore-timeouts 10
--ignore-native-crashes 忽略native层代码的崩溃，情况与4类似，比如adb shell monkey -p xxx.xxx.xxx -v -v -v  --ignore-native-crashes 10
--ignore-security-exceptions 忽略一些许可错误，比如证书许可，网络许可，adb shell monkey -p xxx.xxx.xxx -v -v -v  --ignore-security-exceptions 10
--monitor-native-crashes 是否监视并报告native层发送的崩溃代码，adb shell monkey -p xxx.xxx.xxx -v -v -v  --monitor-native-crashes 10
--kill-procress-after-error 用于在发送错误后杀死进程
--hprof  设置后，在Monkey事件序列之前和之后立即生产分析报告，保存于data/mic目录，不过将会生成大量几兆文件，谨慎使用
--throttle 设置每个事件结束后延迟多少时间再继续下一个事件，降低cpu压力；如果不设置，事件与事件之间将不会延迟，事件将会尽快生成；一般设置300ms，因为人最快300ms左右一个动作，比如 adb shell monkey -p xxx.xxx.xxx -v -v -v  --throttle 300 10
--pct-touch 设置触摸事件的百分比，即手指对屏幕进行点击抬起(down-up)的动作；不做设置情况下系统将随机分配各种事件的百分比。比如adb shell monkey -p xxx.xxxx.xxx --pct-touch 50 -v -v 100 ，这就表示100次事件里有50%事件是触摸事件
--pct-motion 设置移动事件百分比，这种事件类型是由屏幕上某处的一个down事件-一系列伪随机的移动事件-一个up事件，即点击屏幕，然后直线运动，最后抬起这种运动。
--pct-trackball 设置轨迹球事件百分比，这种事件类型是一个或者多个随机移动，包含点击事件，这里可以是曲线运动，不过现在手机很多不支持，这个参数不常用
--pct-syskeys 设置系统物理按键事件百分比，比如home键，音量键，返回键，拨打电话键，挂电话键等
--pct-nav 设置基本的导航按键事件百分比，比如输入设备上的上下左右四个方向键
--pct-appswitch 设置monkey使用startActivity进行activity跳转事件的百分比，保证界面的覆盖情况
--ptc-anyevent 设置其它事件百分比
--ptc-majornav 设置主导航事件的百分比
保存dos窗口打印的monkey信息，在monkey命令后面补上输出地址，如adb shell monkey -p xxx.xxxx.xxx  -v -v 100 > D:\monkey.txt；这样monkey测试结束后，所有打印的信息都会输出到这个文件里
通过adb bugreport 命令可以获取整个android系统在运行过程中所有app的内存使用情况，cpu使用情况，activity运行信息等，包括出现异常等信息。使用方法 adb bugreport > bugreport.txt ;这样在当前目录就会产生一个txt文件和一个压缩包，具体信息可在压缩包查看，txt文件只会记录压缩包的生成过程信息
-f 加载monkey脚本文件进行测试，比如 adb shell monkey -f sdcard/monkey.txt -v -v 500