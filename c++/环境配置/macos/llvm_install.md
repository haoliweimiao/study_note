# LLVM环境搭建

## 下载安装LLVM
+ 使用brew下载安装

~~~ shell
brew install llvm
~~~

## 环境变量配置

~~~ shell
#llvm
$ export PATH="/usr/local/Cellar/llvm/8.0.0_1/bin:$PATH"
$ export CC="/usr/local/Cellar/llvm/8.0.0_1/bin/clang"
$ export CXX="/usr/local/Cellar/llvm/8.0.0_1/bin/clang++"
~~~