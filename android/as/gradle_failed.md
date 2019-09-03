# gradle 编译失败问题汇总


### Could not find manifest-merger.jar
ERROR: Could not find manifest-merger.jar (com.android.tools.build:manifest-merger:26.1.2).
Searched in the following locations:
    https://jcenter.bintray.com/com/android/tools/build/manifest-merger/26.1.2/manifest-merger-26.1.2.jar

修改方式
1. 
build.gradle:
~~~ gradle
buildscript {
    repositories {
		//google()放在前面
		google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.2'
    }
}

allprojects {
    repositories {
		//google()放在前面
        google()
        jcenter()
        //...
    }
}
~~~

2.
gradle-wrapper.properties:
distributionUrl=https\://services.gradle.org/distributions/gradle-4.10.1-all.zip
