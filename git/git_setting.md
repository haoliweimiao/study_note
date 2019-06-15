---
title:git 设置
---

# Git Setting

## 设置代理

~~~ shell
$ git config --global https.proxy localhost:1080(本地ss代理)
$ git config --global http.proxy localhost:1080(本地ss代理)
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