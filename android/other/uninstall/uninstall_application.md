# Android监听自身被卸载与监听其他应用被卸载、安装

+ [监听自身](#监听自身)
+ [监听其他应用](#监听其他应用)

## 监听自身

主要实现思路：

其实我们都知道，Android程序是可以监听到系统卸载程序这个广播的，不过可惜的是，它不能监听到自身被卸载，那么我们要怎么做才能在自身程序被卸载之后做一些事情呢？Java没有说怎么做，那C呢？C是可以的。C的思路是去监听data/data/[packageNmae]这个文件夹的变动情况。

C代码主要如下：

~~~ c
#include <string.h>
#include <jni.h>
 
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include 
#include <unistd.h>
#include <sys inotify.h="">
 
/* 宏定义begin */
// 清0宏
#define MEM_ZERO(pDest, destSize) memset(pDest, 0, destSize)
 
// LOG宏定义
#define LOG_INFO(tag, msg) __android_log_write(ANDROID_LOG_INFO, tag, msg)
#define LOG_DEBUG(tag, msg) __android_log_write(ANDROID_LOG_DEBUG, tag, msg)
#define LOG_WARN(tag, msg) __android_log_write(ANDROID_LOG_WARN, tag, msg)
#define LOG_ERROR(tag, msg) __android_log_write(ANDROID_LOG_ERROR, tag, msg)
 
/* 内全局变量begin */
static char c_TAG[] = onEvent;
static jboolean b_IS_COPY = JNI_TRUE;
 
jstring Java_com_catching_uninstallself_UninstallObserver_startWork(JNIEnv* env,
        jobject thiz, jstring path, jstring url, jint version) {
    jstring tag = (*env)->NewStringUTF(env, c_TAG);
 
    // 初始化log
    LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
            (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, init OK), &b_IS_COPY));
 
    // fork子进程，以执行轮询任务
    pid_t pid = fork();
    if (pid < 0) {
        // 出错log
        LOG_ERROR((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, fork failed !!!), &b_IS_COPY));
    } else if (pid == 0) {
        // 子进程注册目录监听器
        int fileDescriptor = inotify_init();
        if (fileDescriptor < 0) {
            LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                    (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, inotify_init failed !!!), &b_IS_COPY));
 
            exit(1);
        }
 
        int watchDescriptor;
 
        watchDescriptor = inotify_add_watch(fileDescriptor,
                (*env)->GetStringUTFChars(env, path, NULL), IN_DELETE);
        if (watchDescriptor < 0) {
            LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                    (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, inotify_add_watch failed !!!), &b_IS_COPY));
 
            exit(1);
        }
 
        // 分配缓存，以便读取event，缓存大小=一个struct inotify_event的大小，这样一次处理一个event
        void *p_buf = malloc(sizeof(struct inotify_event));
        if (p_buf == NULL) {
            LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                    (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, malloc failed !!!), &b_IS_COPY));
 
            exit(1);
        }
        // 开始监听
        LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, start observer), &b_IS_COPY));
        // read会阻塞进程，
        size_t readBytes = read(fileDescriptor, p_buf,
                sizeof(struct inotify_event));
 
        // 走到这里说明收到目录被删除的事件，注销监听器
        free(p_buf);
        inotify_rm_watch(fileDescriptor, IN_DELETE);
 
        // 目录不存在log
        LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, uninstalled), &b_IS_COPY));
 
        if (version >= 17) {
            // 4.2以上的系统由于用户权限管理更严格，需要加上 --user 0
            execlp(am, am, start, --user, 0, -a,
                    android.intent.action.VIEW, -d,
                    (*env)->GetStringUTFChars(env, url, NULL), (char *) NULL);
        } else {
            execlp(am, am, start, -a, android.intent.action.VIEW,
                    -d, (*env)->GetStringUTFChars(env, url, NULL),
                    (char *) NULL);
        }
        // 扩展：可以执行其他shell命令，am(即activity manager)，可以打开某程序、服务，broadcast intent，等等
 
    } else {
        // 父进程直接退出，使子进程被init进程领养，以避免子进程僵死
    }
 
    return (*env)->NewStringUTF(env, Hello from JNI !);
}
~~~

调用过程：UninstallObserver.java

~~~ java
public class UninstallObserver {
 
    static{
        System.loadLibrary(observer);
    }
     
    public static native String startWork(String path, String url, int version);
}
~~~

调用过程：MainActivity.java

~~~ java
public class MainActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        listening();
    }
     
    private void listening() {
        UninstallObserver.startWork(/data/data/ + getPackageName(), https://www.baidu.com, android.os.Build.VERSION.SDK_INT);
    }
}
~~~

## 监听其他应用

靠系统广播

AndroidManifest文件

~~~ xml
<receiver android:name=".receiver.AppInstallReceiver">
    <intent-filter>
        <!-- 一个新应用包已经安装在设备上，数据包括包名（监听所在的app，新安装时，不能接收到这个广播） -->
        <action android:name="android.intent.action.PACKAGE_ADDED" />
        <!-- 一个新版本的应用安装到设备，替换之前已经存在的版本  替换时会先收到卸载的再收到替换的， 替换自身也能收到-->
        <action android:name="android.intent.action.PACKAGE_REPLACED" />
        <!-- 一个已存在的应用程序包已经从设备上移除，包括包名（卸载监听所在的app，则无法接收到） -->
        <action android:name="android.intent.action.PACKAGE_REMOVED" />
        <data android:scheme="package" />
    </intent-filter>
</receiver>
~~~

AppInstallReceiver文件

~~~ java
public class AppInstallReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();

        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "安装成功" + packageName, Toast.LENGTH_LONG).show();

        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "替换成功" + packageName, Toast.LENGTH_LONG).show();

        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "卸载成功" + packageName, Toast.LENGTH_LONG).show();
        }
    }
}
~~~