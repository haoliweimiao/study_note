# git question

+ [Git如何恢复已经删除的branch](#Git如何恢复已经删除的branch)
+ [Git链接新仓库](#Git链接新仓库)

## Git如何恢复已经删除的branch

~~~ shell
# 创建一个还原的分支
git checkout -b test
# 查看本地的log
git log -g

commit 83c9f554089b43a4f5006614a3a41074fe9de898 (HEAD -> branch_temperature, origin/branch/temperature)
Reflog: HEAD@{0} (Von <von.wu@zkteco.com>)
......

# 还原
git rebase 83c9f554089b43a4f5006614a3a41074fe9de898

~~~

## Git链接新仓库

~~~ shell
# 移除原有链接
git remote remove origin
# 设置新的链接
git remote add origin git@gitlab.xxxxxx.git
~~~