# sqlite

+ [sqlite查看表结构](#sqlite查看表结构)


## sqlite查看表结构
~~~ shell
# 查看所有表的表结构：
select * from sqlite_master where type = "table";
# 查看某个表的表结构
select * from sqlite_master where name = "table_name"
~~~