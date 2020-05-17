cmake_minimum_required(VERSION 2.8.9)

# 需要设置编译环境
set(CMAKE_C_COMPILER "D:/Program Files/Mingw64/bin/gcc.exe")
set(CMAKE_CXX_COMPILER "D:/Program Files/Mingw64/bin/g++.exe")

project (hello)
add_executable(hello hello.cpp)