# vscode 项目过滤文件

配置.vscode setting.json


示例如下 true则不搜索以下文件夹中的内容，工程中隐藏这些文件夹
~~~ json
"search.exclude": {
    "**/node_modules": true,
    "**/bower_components": true,
    "build/": true,
    "temp/": true,
    "library/": true,
    "**/*.anim": true,
    "**/CMakeFiles/": true,
    "**/cmake_install.cmake": true,
    "**/Makefile": true,
    "**/*.includecache": true
  },
  "files.exclude": {
    "**/.git": true,
    "**/.DS_Store": true,
    "**/*.meta": true,
    "library/": true,
    "local/": true,
    "temp/": true,
    "**/CMakeFiles/": true,
    "**/cmake_install.cmake": true,
    "**/Makefile": true
  },
~~~