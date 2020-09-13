# 生成keystore

keytool -genkey -alias android.keystore -keyalg RSA -validity 20000 -keystore android.keystore

(-validity 20000代表有效期天数)，命令完成后，bin目录中会生成android.keystore

查看命令keytool -list -keystore "android.keystore" 输入你设置的keystore密码