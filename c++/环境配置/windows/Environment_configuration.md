# Environment Configuration

Windows下c++环境搭建

## 目录
- [Environment Configuration](#environment-configuration)
  - [目录](#目录)
  - [Cmake配置](#cmake配置)
  - [Mingw64配置](#mingw64配置)
  - [编写hello](#编写hello)


##  Cmake配置
1. 下载地址：https://cmake.org/download/
2. 下载安装完毕，将%Cmake_path%/bin配置进环境变量(%Cmake_path%为cmake安装路径)
3. 重新打开cmd，输入cmake，显示Usage，表示配置成功

## Mingw64配置
1. 离线包下载地址：https://nchc.dl.sourceforge.net/project/mingw-w64/Toolchains%20targetting%20Win64/Personal%20Builds/mingw-builds/8.1.0/threads-posix/seh/x86_64-8.1.0-release-posix-seh-rt_v6-rev0.7z
2. 下载安装完毕，将%mingw64%/bin配置进环境变量(%mingw64%为Mingw64安装路径)
3. 将%mingw64%/bin 中的 mingw32-make.exe 复制一份，重命名为 make.exe
4. 重新打开cmd，输入make，提示make: *** No targets specified and no makefile found.  Stop.，表明配置成功

***mingw安装在C:\Program Files等特殊路径可能会导致无法正常编译等问题***

## 编写hello
参考 win_hello_demo
