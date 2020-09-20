# CMake

+ [指定gcc编译版本](#指定gcc编译版本)

## 指定gcc编译版本

``` cmake
# 修改 CMakeLists.txt 文件，添加如下命令
SET(CMAKE_C_COMPILER “/Users/xxx/gcc-5.2.0/bin/gcc”)
SET(CMAKE_CXX_COMPILER “/Users/xxx/gcc-5.2.0/bin/g++”)
```

``` shell
# 测试可行
export CC=/usr/local/bin/gcc
export CXX=/usr/local/bin/g++
cmake /path/to/your/project
make
```