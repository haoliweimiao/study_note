# Grep

+ [或操作](#或操作)
+ [与操作](#与操作)
+ [其他操作](#其他操作)

## 或操作
~~~ shell
grep -E '123|abc' filename  // 找出文件（filename）中包含123或者包含abc的行
egrep '123|abc' filename    // 用egrep同样可以实现
awk '/123|abc/' filename   // awk 的实现方式
~~~

## 与操作
~~~ shell
grep pattern1 files | grep pattern2 //显示既匹配 pattern1 又匹配 pattern2 的行。
~~~

## 其他操作
~~~ shell
grep -i pattern files   //不区分大小写地搜索。默认情况区分大小写，
grep -l pattern files   //只列出匹配的文件名，
grep -L pattern files   //列出不匹配的文件名，
grep -w pattern files  //只匹配整个单词，而不是字符串的一部分（如匹配‘magic’，而不是‘magical’），
grep -C number pattern files //匹配的上下文分别显示[number]行，
~~~