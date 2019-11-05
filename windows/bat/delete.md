# BAT删除

[遍历删除文件夹](#遍历删除文件夹)
[遍历删除文件](#遍历删除文件)

## 遍历删除文件夹

~~~ bat
@rem 删除当前路径下的所有build文件夹
@echo off
FOR %%b in (./) DO cd /d %%b&for /r %%c in ("build") do if exist %%c rmdir /s/q "%%c"& echo "delete build file in %%c"
~~~

## 遍历删除文件

~~~ bat
@rem 删除该文件下 .iml为后缀的文件
del /S /Q *.iml
~~~