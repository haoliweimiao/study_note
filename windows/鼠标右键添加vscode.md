# Windows系统鼠标右键添加VSCode

Windows系统鼠标右键添加 Open with code 选项

- 右键文件夹，可以使用vscode打开
- 右键单文件，可以使用vscode打开
- 右键空白处，可以使用vscode打开

## 实现

1. 新建一个名为 1.reg 的文件，找一个记事本或者sublime打开，名称无所谓，但是一定需要带上 .reg 后缀。
2. 查看你自己的电脑的vscode安装目录，最简单的方法就是右键桌面的vscode，查看属性就知道了。以我的安装地址为例：C:\\Program Files\\Microsoft VS Code\\Code.exe ， 单反斜杠最好都换成双反斜杠。
3. 复制一下内容到 1.reg 文件中。

~~~ reg
Windows Registry Editor Version 5.00

[HKEY_CLASSES_ROOT\*\shell\VSCode]
@="Open with Code"
"Icon"="C:\\Program Files\\Microsoft VS Code\\Code.exe"

[HKEY_CLASSES_ROOT\*\shell\VSCode\command]
@="\"C:\\Program Files\\Microsoft VS Code\\Code.exe\" \"%1\""

Windows Registry Editor Version 5.00

[HKEY_CLASSES_ROOT\Directory\shell\VSCode]
@="Open with Code"
"Icon"="C:\\Program Files\\Microsoft VS Code\\Code.exe"

[HKEY_CLASSES_ROOT\Directory\shell\VSCode\command]
@="\"C:\\Program Files\\Microsoft VS Code\\Code.exe\" \"%V\""

Windows Registry Editor Version 5.00

[HKEY_CLASSES_ROOT\Directory\Background\shell\VSCode]
@="Open with Code"
"Icon"="C:\\Program Files\\Microsoft VS Code\\Code.exe"

[HKEY_CLASSES_ROOT\Directory\Background\shell\VSCode\command]
@="\"C:\\Program Files\\Microsoft VS Code\\Code.exe\" \"%V\""
~~~

4. 替换所有vscode的安装路径。
5. 双击这个文件，之后都选 “是”。