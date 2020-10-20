# SVN常用命令

+ [将文件checkout到本地目录](#将文件checkout到本地目录)
+ [往版本库中添加新的文件](#往版本库中添加新的文件)
+ [将改动的文件提交到版本库](#将改动的文件提交到版本库)
+ [加锁/解锁](#加锁/解锁)
+ [更新到某个版本](#更新到某个版本)
+ [查看文件或者目录状态](#查看文件或者目录状态)
+ [删除文件](#删除文件)
+ [查看日志](#查看日志)
+ [查看文件详细信息](#查看文件详细信息)
+ [比较差异](#比较差异)
+ [将两个版本之间的差异合并到当前文件](#将两个版本之间的差异合并到当前文件)
+ [SVN帮助](#SVN帮助)
+ [创建分支](#创建分支)

## 将文件checkout到本地目录
svn checkout path（path是服务器上的目录）
简写：svn co

## 往版本库中添加新的文件
svn add file

## 将改动的文件提交到版本库
svn commit -m “LogMessage” [-N] [--no-unlock] PATH(如果选择了保持锁，就使用–no-unlock开关)
简写：svn ci

## 加锁/解锁
svn lock -m “LockMessage” [--force] PATH
svn unlock PATH

## 更新到某个版本
svn update -r m path
简写：svn up

## 查看文件或者目录状态
1）svn status path（目录下的文件和子目录的状态，正常状态不显示）
2）svn status -v path(显示文件和子目录状态)
简写：svn st

## 删除文件
svn delete path -m “delete test fle”
简写：svn (del, remove, rm)

## 查看日志
svn log path

## 查看文件详细信息
svn info path

## 比较差异
svn diff path(将修改的文件与基础版本比较)
svn diff -r m:n path(对版本m和版本n比较差异)
简写：svn di

## 将两个版本之间的差异合并到当前文件
svn merge -r m:n path

## SVN帮助
svn help
svn help ci

## 创建分支
``` shell
svn cp -m "create branch"  http://server/test/trunk  http://server/test/branches/future
```