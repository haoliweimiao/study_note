# gcc环境搭建
**安装系统MACOS**

## 下载安装gcc
+ 使用brew下载安装

~~~ shell
brew install gcc@5
~~~

此处下载5.5.0版本的gcc，若需要下载其他版本，可以使用如下指令搜索

~~~ shell
brew search gcc
~~~

## 配置gcc环境
在Terminal中输出gcc，按tab键补全，可以看到以下指令
~~~ shell
gcc-5         gcc-ar-5      gcc-nm-5      gcc-ranlib-5
~~~

在环境中将gcc*5配置成gcc

编辑环境变量
~~~
$ vim ~/.bash_profile
~~~

插入以下数据
~~~ shell
# gcc环境变量配置
$ export PATH="/usr/local/Cellar/gcc@5/5.5.0_3/bin:$PATH"
# 替换安装的gcc-5 替代系统默认的gcc
$ alias gcc='gcc-5'
$ alias g++='g++-5'
$ alias c++='c++-5'
~~~

确定环境
~~~ shell
$ .  ~/.bash_profile
$ gcc -v

$ Using built-in specs.
$ COLLECT_GCC=gcc-5
$ //...省略
$ gcc version 5.5.0 (Homebrew GCC 5.5.0_3) 
~~~
