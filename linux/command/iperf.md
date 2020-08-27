# iperf

+ [安装](#安装)
+ [测试](#测试)
+ [参数说明](#参数说明)
    + [通用参数](#通用参数)
    + [server参数](#server参数)
    + [client参数](#client参数)

## 安装

linux OS install iperf
``` shell
# install iperf 2.*
brew install iperf

# install iperf 3.*
brew install iperf3
```

android OS install iperf

[download url](!https://github.com/KnightWhoSayNi/android-iperf/tree/gh-pages/libs)
``` shell
# To upload binary file to an Android device

adb push <LOCAL_PATH_TO_BINARY_FILE> /data/local/tmp/<BINARY_NAME>
adb shell chmod 777 /data/local/tmp/<BINARY_NAME>
# Set a default iPerf2 version

adb shell ln -s /data/local/tmp/<IPERF2_BINARY_NAME> iperf
# Executing iPerf2

adb shell /data/local/tmp/iperf <IPERF_ARGUMENTS>
# Set a default iPerf3 version

adb shell ln -s /data/local/tmp/<IPERF3_BINARY_NAME> iperf3
# Executing iPerf3

adb shell /data/local/tmp/iperf3 <IPERF_ARGUMENTS>
```

## 测试

+ ***需要保持服务端客户端iperf版本一致，否则会无法连接***
+ ***默认开启tcp方式，需要使用udp方式，加入命令 --udp ***

server
``` shell
iperf -s --bandwidth 20M                                                                          
------------------------------------------------------------
Server listening on TCP port 5001
TCP window size: 1.00 MByte (default)
------------------------------------------------------------
```

client
``` shell
iperf --client 192.168.8.104 --time 100 --port 5001 --parallel 1 --interval 1 --bandwidth 20M -f k
```



## 参数说明

-s 以server模式启动，eg：iperf -s

-c host以client模式启动，host是server端地址，eg：iperf -c 222.35.11.23

### 通用参数

-f [kmKM] 分别表示以Kbits, Mbits, KBytes, MBytes显示报告，默认以Mbits为单位,eg：iperf -c 222.35.11.23 -f K

-i sec 以秒为单位显示报告间隔，eg：iperf -c 222.35.11.23 -i 2

-l 缓冲区大小，默认是8KB,eg：iperf -c 222.35.11.23 -l 16

-m 显示tcp最大mtu值

-o 将报告和错误信息输出到文件eg：iperf -c 222.35.11.23 -o ciperflog.txt

-p 指定服务器端使用的端口或客户端所连接的端口eg：iperf -s -p 9999;iperf -c 222.35.11.23 -p 9999

-u 使用udp协议

-w 指定TCP窗口大小，默认是8KB

-B 绑定一个主机地址或接口（当主机有多个地址或接口时使用该参数）

-C 兼容旧版本（当server端和client端版本不一样时使用）

-M 设定TCP数据包的最大mtu值

-N 设定TCP不延时

-V 传输ipv6数据包

### server参数

-D 以服务方式运行iperf，eg：iperf -s -D

-R 停止iperf服务，针对-D，eg：iperf -s -R

### client参数

-d 同时进行双向传输测试

-n 指定传输的字节数，eg：iperf -c 222.35.11.23 -n 100000

-r 单独进行双向传输测试

-t 测试时间，默认10秒,eg：iperf -c 222.35.11.23 -t 5

-F 指定需要传输的文件

-T 指定ttl值