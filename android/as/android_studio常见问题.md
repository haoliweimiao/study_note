---
title: android studio 常见异常 
---
# android studio 常见异常

---
### Android Studio Java代码报红
（所有第三方库引入失败），但可正常运行解决方法（4种方式）

    一.从Android Studio层面解决问题: 删除user目录下.AndroidStudio中的文件
    二. 从Project 项目层面解决问题: 清除项目缓存
    三. 从Gradle层面解决问题: 清除user目录下.gradle文件,重新下载Gradle
    删除工程目录下.gradle、.idea
---
### error:Timeout waiting to lock file hash cache 
    Timeout waiting to lock file hash cache (/Users/hlw/.gradle/caches/4.4/fileHashes)

    【File】–>【Invalidate Caches/Restart】

---
### error: found an invalid color.
    Error: java.util.concurrent.ExecutionException: com.android.builder.internal.aapt.v2.Aapt2Exception: 
    AAPT2 error: check logs for details

    1.检查.9图片黑线是否正常添加

---
### AAPT2 error: check logs for details
    设置install run 勾全部去除

---
### error:createProcessor error = 2
    setting->Appearance&Behavior->System Settings->Android SDK->SKD Tool->NDK

    解决方法：配置ndk-16 之前的ndk-17版本缺少东西，有问题

### It is currently in use by another Gradle instance

    find ~/.gradle -type f -name "*.lock" | while read f; do rm $f; done


### Could not find manifest-merger.jar (com.android.tools.build:manifest-merger:26.1.2). Searched in the following locations: https://jcenter.bintray.com/com/android/tools/build/manifest-merger/26.1.2/manifest-merger-26.1.2.jar

最外层 build.gradle: google()需要放在jcenter()前面

### ERROR: Timeout waiting to lock Build Output Cleanup Cache (*/.gradle/buildOutputCleanup). It is currently in use by another Gradle instance.

find ~/.gradle -type f -name "*.lock" -delete

ps -A | grep gradle
sudo kill -9 <process ID>

### buildOutput.apkData must not be null

删除build文件下outputs文件夹

### ERROR: Cause: unable to find valid certification path to requested target

+ 手动添加证书
+ jcenter() > jcenter{ url 'http://jcenter.bintray.com' } 
+ 切换编译的jdk，不用Android Studio自带的

### env: bash\r: No such file or directory

Android Studio 脚本打包出现该错误，解决方法：
1. 新建一个Android项目，将新项目中的gradlew文件复制进来
2. chmod 777 **.sh

### Location specified by ndk.dir (**) did not contain a valid NDK and and couldn't be used

在local.properties文件中，ndk.dir项填入正确的ndk路径(项目未使用到ndk，可以直接删除该项)