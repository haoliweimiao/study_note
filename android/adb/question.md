# 问题

## 关于adb remount 后system仍然为Read-Only file system的解决方法

``` shell
$ adb root 
$ adb disable-verity 
# (最新的adb 工具包才支持adb disable-verity命令，如果是Linux开发环境，
# 则可使用工程编译结果目录out/host/linux-x86/bin下的adb执行文件） 
$ adb reboot 重启设备 
$ adb root 
$ adb remount 
```