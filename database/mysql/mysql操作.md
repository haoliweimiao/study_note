---
title:mysql操作
---
# mysql操作

### 修改密码
``` shell
/usr/local/Cellar/mysql@5.6/5.6.47/support-files stop
# 第一个终端界面
/usr/local/Cellar/mysql@5.6/5.6.47/bin/mysqld_safe --skip-grant-tables
# 第二个终端界面
UPDATE mysql.user SET authentication_string=PASSWORD('root') WHERE User='root';
UPDATE mysql.user SET PASSWORD=PASSWORD('root') WHERE User='root';
FLUSH PRIVILEGES;
\q
# 关闭第一个终端界面
```