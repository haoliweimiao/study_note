# 按需要修改成本机make地址，mingw安装在C:\Program Files等特殊路径可能会导致无法正常编译等问题
cmake -G "MinGW Makefiles" -D"CMAKE_MAKE_PROGRAM:PATH=F:/software/mingw64/bin/make.exe" .