# Gradle问题

+ [代理配置](#代理配置)
+ [删除代理配置](#删除代理配置)
+ [gradle下载](#gradle下载)

## 代理配置
``` shell
open ~/.gradle/gradle.properties
```

添加内容如下
```
systemProp.http.proxyHost=127.0.0.1
systemProp.http.proxyPort=1080
systemProp.https.proxyHost=127.0.0.1
systemProp.https.proxyPort=1080
```

## 删除配置文件
~/.gradle/gradle.properties
文件内将代理删除

## gradle下载
+ gradle下载下载速度慢
`可在as中gradle运行，生成相应版本的gradle文件夹，再将下载好的gradle-**.zip文件放入，重新编译即可`