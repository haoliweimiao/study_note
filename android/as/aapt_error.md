---
title:aapt error
---

# AAPT Error 处理

## 排查编译版本问题

~~~ xml
buildToolsVersion '28.0.3'
该版本慎用，很多引用不规范错误会报错，导致无法编译成功

gradlew processDebugResources --debug
使用改方法可以将错误指出，打印出的文本搜索aapt
~~~

