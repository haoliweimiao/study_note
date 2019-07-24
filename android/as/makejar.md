# Android Studio MakeJar

因为IDE环境问题 classes.jar生成的位置也不同否则构建工程时候会报错


## Android Studio3.1.2之前的配置
~~~ shell
def SDK_BASENAME = "myjar";
def SDK_VERSION = "_v1.0.0";
def sdkDestinationPath = "build/outputs/jar/";
def zipFile = file('build/intermediates/bundles/release/classes.jar')
task deleteBuild(type: Delete) {
    delete sdkDestinationPath + SDK_BASENAME + SDK_VERSION + ".jar"
}
task makeJar(type: Jar) {
    from zipTree(zipFile)
 from fileTree(dir: 'src/main', includes: ['assets/**']) // 打包assets目录下的所有文件

 baseName = SDK_BASENAME + SDK_VERSION
    destinationDir = file(sdkDestinationPath)
}
makeJar.dependsOn(deleteBuild, build)
~~~

## Android Studio3.1.2之后的配置
~~~ shell
def SDK_BASENAME = "myjar";
def SDK_VERSION = "_v1.0.0";
def sdkDestinationPath = "build/outputs/jar/";
def zipFile = file('build/intermediates/packaged-classes/release/classes.jar')
task deleteBuild(type: Delete) {
    delete sdkDestinationPath + SDK_BASENAME + SDK_VERSION + ".jar"
}
task makeJar(type: Jar) {
    from zipTree(zipFile)
 from fileTree(dir: 'src/main', includes: ['assets/**']) // 打包assets目录下的所有文件

 baseName = SDK_BASENAME + SDK_VERSION
    destinationDir = file(sdkDestinationPath)
}
makeJar.dependsOn(deleteBuild, build)
~~~

