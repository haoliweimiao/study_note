# c自动补全问题

+ 关于c代码自动补全出现不提示引入的第三方库的问题
原因：由于安装了 C/C++ Clang Command Adapter，该插件的补全无法将第三方库的方法提示(应该是未设置好的原因)
解决方法：禁用 C/C++ Clang Command Adapter 插件，在设置中将VSCode自带的C_CPP:Autocomplete功能打开(默认关闭)

+ **MACOS 需要重新设置以下自动补全提示按键 Control + Space 与系统快捷键冲突**


## 自动补全速度慢的问题
+ c_cpp_properties.json 设置导致
+ ***之前使用gcc，更换为clang后速度变快***
+ "intelliSenseMode": "clang-x64",
  "compilerPath": "/usr/local/Cellar/llvm/8.0.0_1/bin/clang++",