# git 操作说明
+ [查看远程分支](#查看远程分支)
+ [查看本地分支](#查看本地分支)
+ [设置远程地址](#设置远程地址)
+ [创建分支](#创建分支)
+ [切换分支到test](#切换分支到test)
+ [删除本地分支](#删除本地分支)
+ [删除远程分支](#删除远程分支)
+ [查看本地和远程分支](#查看本地和远程分支)
+ [提交本地分支到远程](#提交本地分支到远程)
+ [提交pr](#提交pr)
+ [移除本地commit](#移除本地commit)
+ [存储修改](#存储修改)
+ [删除文件夹](#删除文件夹)
+ [删除远程仓库的错误提交](#删除远程仓库的错误提交)
+ [Git如何恢复已经删除的branch](#Git如何恢复已经删除的branch)
+ [Git链接新仓库](#Git链接新仓库)
+ [合并合并请求](#合并合并请求)
+ [无法切换分支](#无法切换分支)
+ [切换仓库](#切换仓库)

## 查看远程分支
~~~ shell
$ git branch -a
~~~

## 查看本地分支
~~~ shell
$ git branch
~~~

## 设置远程地址
~~~
$ git remote set-url origin 你的远端地址 
~~~

## 创建分支
~~~ shell
$ git branch test
$ git branch
~~~

## 切换分支到test
~~~ shell
$ git branch
$ git checkout test
~~~

## 删除本地分支   
~~~ shell
$ git branch -d xxxxx
~~~

## 删除远程分支
~~~ shell
//删除远程分支
$ git branch -r -d origin/branch-name
$ git push origin :branch-name
//删除远程分支
$ git push origin --delete [branch_name]
~~~

## 查看本地和远程分支 
~~~ shell 
-a。前面带*号的代表你当前工作目录所处的分支
~~~

## 提交pr
~~~ shell
$ git push origin 本地仓库:名称

$ git submodule update --init
~~~

## 移除本地commit
~~~ shell
$ git reset HEAD~ 
~~~ 

## 存储修改
~~~ shell
$ git stash
$ git stash list 查看列表
$ git stash clear 清空
~~~

## 提交本地分支到远程
~~~ shell
# 提交test分支到远程仓库
$ git checkout b test
$ git push origin HEAD -u

# 创建并切换到分支feature-branch
$ git checkout -b feature-branch
# 推送本地的feature-branch(冒号前面的)分支到远程origin的feature-branch(冒号后面的)分支(没有会自动创建)
$ git push origin feature-branch:feature-branch
~~~

## 删除文件夹

~~~ shell
$ git rm -r one-of-the-directories
$ git commit . -m "Remove duplicated directory"
$ git push origin <your-git-branch> (typically 'master', but not always)
~~~

## 删除远程仓库的错误提交
```不是必须要回退，不建议这样做！！！```
~~~ shell
# 强制返回上次的版本（~1回退到上一次提交，~2回退到上两次提交，以此类推）
git reset --hard HEAD~1
# 将本次变更强行推送至服务器；这样在服务器上就能回退到你想回退的位置。
git push --force
~~~



## Git如何恢复已经删除的branch

~~~ shell
# 创建一个还原的分支
git checkout -b test
# 查看本地的log
git log -g

commit 83c9f554089b43a4f5006614a3a41074fe9de898 (HEAD -> branch_temperature, origin/branch/temperature)
Reflog: HEAD@{0} (XXX <xxx.wu@xxx.com>)
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

## 合并合并请求
``` shell
git fetch origin
git checkout -b bugfix origin/bugfix
git checkout dev
git merge --no-ff bugfix
git push origin dev
```


## 无法切换分支

git切换分支报错：error: The following untracked working tree files would be overwritten by checkout

``` shell
(慎用，会直接删除本地git未跟踪的文件，可用来清除merge错误冲突)
git clean -d -fx
```

 git clean 参数 
    -n 显示将要删除的文件和目录；
    -x -----删除忽略文件已经对git来说不识别的文件
    -d -----删除未被添加到git的路径中的文件
    -f -----强制运行

## 切换仓库
1. cd进入项目目录
2. ls -a 获取所有的文件
3. 进入.git文件夹，打开config文件
4. 修改下面文件中的remote “origin”中的url为自己的url地址
5. 然后进行git push