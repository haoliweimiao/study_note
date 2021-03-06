# lintoptions

~~~ gradle
android {
    lintOptions {
        // true--关闭lint报告的分析进度
        quiet true
        // true--错误发生后停止gradle构建
        abortOnError false
        // true--只报告error
        ignoreWarnings true
        // true--忽略有错误的文件的全/绝对路径(默认是true)
        //absolutePaths true
        // true--检查所有问题点，包含其他默认关闭项
        checkAllWarnings true
        // true--所有warning当做error
        warningsAsErrors true
        // 关闭指定问题检查(根据id指定)
        disable 'TypographyFractions','TypographyQuotes'
        // 打开指定问题检查(根据id指定)
        enable 'RtlHardcoded','RtlCompat', 'RtlEnabled'
        // 仅检查指定问题(根据id指定)
        check 'NewApi', 'InlinedApi'
        // 在报告中返回对应的Lint说明
        explainIssues true
        // true--error输出文件不包含源码行号
        noLines true
        // true--显示错误的所有发生位置，而不是截取短列表
        showAll true
        // 回退lint设置(默认规则)
        lintConfig file("default-lint.xml")
        // true--生成txt格式报告(默认false)
        textReport true
        // 重定向输出；可以是文件或'stdout'
        textOutput 'stdout'
        // true--生成XML格式报告
        xmlReport false
        // 指定xml报告文档(默认lint-results.xml)
        xmlOutput file("lint-report.xml")
        // true--生成HTML报告(带问题解释，源码位置，等)
        htmlReport true
        // html报告可选路径(构建器默认是lint-results.html )
        htmlOutput file("lint-report.html")
        //  true--所有正式版构建执行规则生成崩溃的lint检查，如果有崩溃问题将停止构建
        checkReleaseBuilds true
        // 在发布版本编译时检查(即使不包含lint目标)，指定问题的规则生成崩溃
        // fatal 'NewApi', 'InlineApi'
        fatal 'NewApi', 'InlinedApi'
        // 指定问题的规则生成错误
        error 'Wakelock', 'TextViewEdits'
        // 指定问题的规则生成警告
        warning 'ResourceAsColor'
        // 忽略指定问题的规则(同关闭检查)
        ignore 'TypographyQuotes'
        // 覆盖 Lint 规则的严重程度，例如：
        // severityOverrides ["MissingTranslation": LintOptions.SEVERITY_WARNING]
    }
}
~~~

## 同一工程统一配置lintOptions

根路径创建 android-lint.gradle，内部加入lintOptions规则
子模块添加

~~~ gradle
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
// 添加此行
apply from: '../android-lint.gradle'
~~~