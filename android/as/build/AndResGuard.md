# AndResGuard

## 什么是AndResGuard

AndResGuard是一个缩小APK大小的工具，它的原理类似Java Proguard，但是只针对资源。它会将原本冗长的资源路径变短，例如将res/drawable/wechat变为r/d/a。

## AndResGuard的配置

项目根目录下build.gradle中，添加插件的依赖：
``` gradle
 dependencies {
        classpath 'com.tencent.mm:AndResGuard-gradle-plugin:1.2.10'
    }
```

and_res_guard.gradle
``` gradle
apply plugin: 'AndResGuard'

andResGuard {
    mappingFile = null
    use7zip = true
    useSign = true
    keepRoot = false
    compressFilePattern = [
            "*.png",
            "*.jpg",
            "*.jpeg",
            "*.gif",
            "resources.arsc"
    ]
    whiteList = [
            // your icon
            "R.drawable.icon",
            // for fabric
            "R.string.com.crashlytics.*",
            // for umeng update
            "R.string.tb_*",
            "R.layout.tb_*",
            "R.drawable.tb_*",
            "R.drawable.u1*",
            "R.drawable.u2*",
            "R.color.tb_*",
            // umeng share for sina
            "R.drawable.sina*",
            // for google-services.json
            "R.string.google_app_id",
            "R.string.gcm_defaultSenderId",
            "R.string.default_web_client_id",
            "R.string.ga_trackingId",
            "R.string.firebase_database_url",
            "R.string.google_api_key",
            "R.string.google_crash_reporting_api_key",

            //友盟
            "R.string.umeng*",
            "R.string.UM*",
            "R.layout.umeng*",
            "R.drawable.umeng*",
            "R.id.umeng*",
            "R.anim.umeng*",
            "R.color.umeng*",
            "R.style.*UM*",
            "R.style.umeng*",

            //融云
            "R.drawable.u*",
            "R.drawable.rc_*",
            "R.string.rc_*",
            "R.layout.rc_*",
            "R.color.rc_*",
            "R.id.rc_*",
            "R.style.rc_*",
            "R.dimen.rc_*",
            "R.array.rc_*"
    ]

    sevenzip {
        artifact = 'com.tencent.mm:SevenZip:1.2.10'
        //path = "/usr/local/bin/7za"
    }
}

```

use
``` gradle
// 注意路径
apply from: 'and_res_guard.gradle'
```