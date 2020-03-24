# String

+ [添加特殊字符](#添加特殊字符)
+ [String.format](#String.format)

## 添加特殊字符

示例
~~~ xml
 <string name="aiyisi_tip">\t\t爱义思（上海）科技发展有限公司,国家级高新技术企业，成立于2012年。\n\t\t爱义思来自Acesmart的音译。\n\t\t爱义思的使命是：一流智慧产品 创造美好生活，让生活不在难！\n\t\t爱义思奉行“敬天爱人、诚信守义、思而智慧、慧泽天下“的核心价值观。\n
     \t\t今后一二十年，人类将进入机器人服务家庭生活的新时代。\n\t\t爱义思的愿景是；让新物种机器人服务亿万家庭！\n
     \t\t爱义思应用“深度感知、高度智能、广度互联”的核心技术，将逐步发展智慧保健、智慧卧室、智慧厨房、智慧卫浴、智慧客厅、智慧书房、智慧储物、智慧学习、智慧安防、智慧娱乐等家庭10大场景里的服务机器人和智慧产品，为人类创造更健康、更便捷、更舒适、更安全、更快乐的生活方式。
</string>
~~~

|符号|功能|
|:-|:-|
| \t             | 为tab键，即空格键，不分行；| 
| \n             | 为换行符| 
| &#160;         | 表示空格键| 


## String.format

+ 整数型
~~~ xml
<string name="alert">I am %1$d years old</string> 
~~~

~~~ java
int nAge=23;
String sAgeFormat = getResources().getString(R.string.alert);
String sFinalAge = String.format(sAgeFormat, nAge);
~~~

+ 字符串型

~~~ xml
<string name="alert2">My name is %1$s , I am form %2$s</string>
~~~

~~~ java
String sName="abc"
String sCity="Beijing"
String sInfoFormat = getResources().getString(R.string.alert2);
String sFinalInfo=String.format(sInfoFormat, sName, sCity);
~~~
