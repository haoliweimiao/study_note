---
title:git 设置
---

# Git Setting

## 设置代理

~~~ shell
# 小飞机开启代理设置
$ git config --global https.proxy localhost:1080(本地ss代理)
$ git config --global http.proxy localhost:1080(本地ss代理)
# 未测试
$ git config --global https.proxy sock5://localhost:1080
$ git config --global http.proxy sock5://localhost:1080
# 取消代理
$ git config --global  --unset  http.proxy
$ git config --global  --unset  https.proxy
~~~

## 查看系统config
~~~ shell
$ git config --system --list
~~~


## 查看当前用户（global）配置
~~~ shell
$ git config --global  --list
~~~

## 查看当前仓库配置信息
~~~ shell
$ git config --local  --list
~~~

## 删除当前用户（global）配置
~~~ shell
$ git config --global --unset http.proxy
~~~