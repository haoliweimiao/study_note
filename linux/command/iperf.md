# iperf

+ [安装](#安装)
+ [测试](#测试)

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

***需要保持服务端客户端iperf版本一致，否则会无法连接***

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
iperf --client 192.168.8.104 --time 100 --port 5001 --parallel 1 --interval 1 --udp --bandwidth 20M -f k
```