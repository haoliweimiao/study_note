# Activity启动

+ [Activity启动简述](#Activity启动简述)

## Activity启动简述

1~3 Launcher与AMS(ActivityManagerService)相互通讯
4~7 App和AMS相互通讯

1) Launcher通知AMS，要启动App，而且指定要启动App的哪个页面(也就是首页)。
2) AMS通知Launcher，“好了我知道了，没你什么事了”。同时，把要启动的首页记下来。
3) Launcher当前页面进入Paused状态，然后通知AMS，“我睡了，你可以去找App了”。
4) AMS检查App是否已经启动了。是，则唤起App即可。否，就要启动一个新的进程。AMS在新进程中创建一个ActivityThread对象，启动其中的main函数。
5) App启动后，通知AMS，“我启动好了”。
6) AMS翻出之前在2)中存的值，告诉App，启动哪个页面。
7) App启动首页，创建Context并与首页Activity关联。然后调用首页的OnCreate函数。
