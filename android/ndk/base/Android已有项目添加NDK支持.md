# Android已有项目添加NDK支持

1. 项目添加NDK路径：File->Project Structrue ->SDK Location ->设置NDK路径；
2. 生成一个CMakeLists.txt文件，并放入根目录（和src目录同级）
3. build.gradle添加如下内容
~~~ gradle
externalNativeBuild {
    cmake {
        path file('CMakeLists.txt')
    }
}
~~~

4. 创建一个java文件，例如：OKJNIUtils.java，放在com.tttxxx.onekey这个包下，并写个hello函数
~~~ java
package com.tttxxx.onekey;
public class OKJNIUtils {
        //需要加载这个，才能使用里面的函数
        static{
            System.loadLibrary("okGKLib");
        }
        //这个和c++里面的函数名字，有对应关系。到时候调用安卓里面的函数hello，就能调用到C++里面的函数
        public native String hello();
}
~~~

5.src目录的main目录 下面创建cpp文件夹,创建一个cpp文件，例如：
~~~ c
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
JNICALL Java_com_tttxxx_onekey_OKJNIUtils_hello(JNIEnv *env,jobject ) {
             std::string hello ="Hello from C++";
              return env->NewStringUTF(hello.c_str());
}
~~~

这个文件命名，可以自己命名，也可以用javah工具生成，https://blog.csdn.net/icewst/article/details/103855283

这个函数的命名，对应了一个java的类，com.tttxxx.onekey 下面的OKJNIUtils.java
这里面有一个hello函数。

CMakeLists.txt内容示例：
~~~ cmake
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             okGKLib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/okGKLib.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       okGKLib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
~~~