---
title:git 错误示例
---

# git 错误示例

## You have not concluded your merge (MERGE_HEAD exists).

~~~ shell
解决办法一:保留本地的更改,中止合并->重新合并->重新拉取
$:git merge --abort
$:git reset --merge
$:git pull
~~~

警告：运行git-merge时含有大量的未commit文件很容易让你陷入困境，这将使你在冲突中难以回退。
因此非常不鼓励在使用git-merge时存在未commit的文件，建议使用git-stash命令将这些未commit文件暂存起来，
并在解决冲突以后使用git stash pop把这些未commit文件还原出来。

~~~ shell
解决办法二:舍弃本地代码,远端版本覆盖本地版本(慎重)
$:git fetch --all
$:git reset --hard origin/master
$:git fetch
~~~

---

## fatal: No url found for submodule path 'zkcore_library' in .gitmodules

~~~ shell
$ git rm --cached path_to_submodule (git rm --cached zkcore_library)
需要确认该模块是否依然在git上存在，可能是被删除导致的
~~~

## SSL_connect: SSL_ERROR_SYSCALL in connection to github.com:443

检查设备是否开启代码，检查git是否配置代理
``` shell
# 检测配置文件
git config --global --list
# 取消代理
git config --global --unset http.proxy
git config --global --unset https.proxy
```

代理开启，检测git配置的代理端口号是否一致
