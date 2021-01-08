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


### Could not create service of type DefaultGeneralCompileCaches using GradleScopeCompileServices.createGeneralCompileCaches().

~~~ shell
> Task :app:compileDebugJavaWithJavac FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:compileDebugJavaWithJavac'.
> Could not create service of type DefaultGeneralCompileCaches using GradleScopeCompileServices.createGeneralCompileCaches().

# 解决方法，执行一次一下命令，并且编译通过
./gradlew compileDebugJavaWithJavac --stacktrace
~~~

### No toolchains found in the NDK toolchains folder for ABI with prefix

~~~ shell
# on Mac
cd  ~/Library/Android/sdk/ndk-bundle/toolchains
ln -s aarch64-linux-android-4.9 mips64el-linux-android
ln -s arm-linux-androideabi-4.9 mipsel-linux-android
~~~

### kotlin 插件导致的无法编译问题

``` gradle
repositories {
        # 添加该仓库
        mavenCentral()
        google()
        jcenter()
    }
dependencies {
        classpath 'com.android.tools.build:gradle:3.1.2'
        # 切换可用版本
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72"
    }
```

### Could not resolve com.android.support:appcompat-v7:26.+.

~~~ gradle
# implementation 'com.android.support:appcompat-v7:26.+'
# 修改
implementation 'com.android.support:appcompat-v7:26.1.0'
~~~

### Cannot access androidx.lifecycle.HasDefaultViewModelProviderFactory

引入
``` gradle
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'androidx.lifecycle:lifecycle-runtime:2.2.0'
```


### Failed to read PNG signature: file does not start with PNG signature

``` gradle
 buildTypes {
        debug {
           // ...
        }

        release {
            // 此处修改部分png图片在打包时报错导致无法输出apk的问题
            crunchPngs false
           // ...
        }
    }
```