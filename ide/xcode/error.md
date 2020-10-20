# error

## cmake转xcode项目失败

``` cmake
# 在project 之前加入以下内容
SET (CMAKE_C_COMPILER_WORKS 1)
SET (CMAKE_CXX_COMPILER_WORKS 1)
set(CMAKE_TRY_COMPILE_TARGET_TYPE "STATIC_LIBRARY")
project(xxxProject)
```