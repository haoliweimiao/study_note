# Java

## 卸载

### MACOS
1. 单击位于停靠栏中的 Finder 图标
2. 在 Finder 菜单中单击前往
3. 单击实用工具
4. 双击终端图标
5. 在“终端”窗口中，复制并粘贴以下命令：
``` shell
# 请勿尝试通过从 /usr/bin 删除 Java 工具来卸载 Java。此目录是系统软件的一部分，下次对操作系统执行更新时，Apple 会重置所有更改。
sudo rm -fr /Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin
sudo rm -fr /Library/PreferencePanes/JavaControlPanel.prefPane
sudo rm -fr ~/Library/Application\ Support/Oracle/Java
sudo rm -rf /Library/Java/JavaVirtualMachines/jdk<version>.jdk
```