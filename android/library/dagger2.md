# Dagger

Google Dagger2

+ [命名规约](#命名规约)

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