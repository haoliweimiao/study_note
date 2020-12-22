# keytool

## 生成密钥命令
``` shell
# -k 表示要生成的 keystore 文件的名字，这里为 test.keystore
# -p 表示要生成的 keystore 的密码，这里为 123456
# -pk8 表示要导入的 platform.pk8 文件
# -cert 表示要导入的platform.x509.pem
# -alias 表示给生成的 keystore 取一个别名，这是命名为 xiaxl
./keytool-importkeypair -k create.keystore -p 123456 -pk8 platform.pk8 -cert platform.x509.pem -alias test
```

