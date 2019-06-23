# CMakeLists.txt
# CMake 最低版本号要求
cmake_minimum_required(VERSION 3.15)

# 项目信息
project(main)

# include路径
include_directories(
    ${PROJECT_SOURCE_DIR}/src )

# 把 第三方 库 的 .a 文件和 .h 文件放到 代码的 lib 文件夹
link_directories(
    ${PROJECT_SOURCE_DIR}/lib)

#使用命令 ADD_SUBDIRECTORY 指明本项目包含一个子目录 src 
ADD_SUBDIRECTORY( src )

# 查找当前目录下的所有源文件
# 并将名称保存到 DIR_LIB_SRCS 变量
aux_source_directory(. DIR_SRCS)

# 指定生成目标 
#ADD_EXECUTABLE 指示变量 DIR_SRCS 中的源文件需要编译 成一个名称为 main 的可执行文件。
ADD_EXECUTABLE(main ${DIR_SRCS})

#开启调试，不需要则去除
#set(CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS} -g)

#使用命令 TARGET_LINK_LIBRARIES 指明可执行文件 main 需要连接一个名为Test的链接库 。
#TARGET_LINK_LIBRARIES( main libglad.a )

#查找库所在目录FIND_LIBRARY
#FIND_LIBRARY(
#	/usr/local/lib 
#	/Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX10.13.sdk/usr/include)


TARGET_LINK_LIBRARIES( main glad glfw MAIN_SRC_LIB_FILE )