DATA UPDATE COMPANY CompanyId=1	CompanyName=ZKTeco	CompanyRut=Rut	CompanyAddress=China
DATA DELETE COMPANY CompanyId=1
DATA UPDATE HTTPS Open=0
DATA UPDATE PRINT UserName=1	Run=1	PunchDate=0	PunchTime=0	CompanyName=0	CompanyRut=0	
CompanyAddress=1	VerificationMode=0	Hash=0	FixTitle=0	Reserved=0	PuchType=1

| success  | failed |
| :-: | :-: |
|successful ticket | failed ticket|
|user name （用户名称）||
|user id | |
|user run （用户号码类似身份证）||
|Punch Date: DD/MM/YYYYY |punch date DD/MM/YYYYY 打卡日期|
|punch time: HH:mm:ss |punch time HH:mm:ss  打卡时间|
|Verification status||
|CompanyRUT|CompanyRUT公司税号|
|CompanyName|CompanyName公司名称|
|CompanyAddress|CompanyAddress公司地址|
||CompanyID公司的ID|
|title (0 关闭 1 门禁控制 2 用户定义)|Failed ticket title 可以编辑内容|
||verification mode 验证模式|
||Error code: hash值不经过MD5加密 错误代码|
||hash|
||??SN 序列号|
||??打卡类型: 失败 (西语: Estado de marca: Marcación fallida) |