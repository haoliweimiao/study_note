# Dagger

Google Dagger2

+ [命名规约](#命名规约)
+ [问题]](#问题)

## 命名规约

+ @Provides   方法用provide前缀命名
    ``` java
        @Provides
        public String provideMethodName(){
            return "MethodName";
        }
    ```
+ @Module     用Module后缀命名
    ``` java
        @Module
        public class AModule{
            // ...
        }
    ```
+ @Component  以Component作为后缀
    ``` java
        @Component
        public class AComponent{
            // ...
        }
    ```


## 问题
1. 出现Presenter无法注入的问题
    + Presenter实现类未使用@Inject注入构造方法
    + Presenter实现类构造方法存在无法注入的类型(例如：提供Activity，构造注入Context)