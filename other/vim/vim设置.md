---
title:vim设置
---
# vim设置

set | 功能描述
:- | :- |
set nocompatible         |        "去掉有关vi一致性模式，避免以前版本的bug和局限    
set nu!                  |                  "显示行号
set guifont=Luxi/ Mono/ 9  | " 设置字体，字体名称和字号
filetype on               |               "检测文件的类型     
set history=1000      |            "记录历史的行数
set background=dark   |       "背景使用黑色
syntax on      |                          "语法高亮度显示
set autoindent     |                  "vim使用自动对齐，也就是把当前行的对齐格式应用到下一行(自动缩进）
set cindent        |                     "（cindent是特别针对 C语言语法自动缩进）
set smartindent  |                  "依据上面的对齐格式，智能的选择对齐方式，对于类似C语言编写上有用
set tabstop=4     |                   "设置tab键为4个空格，
set shiftwidth =4  |                 "设置当行之间交错时使用4个空格
set ai!         |                             " 设置自动缩进 
set showmatch     |                "设置匹配模式，类似当输入一个左括号时会匹配相应的右括号
set guioptions-=T  |              "去除vim的GUI版本中得toolbar   |
set vb t_vb=         |                   "当vim进行编辑时，如果命令错误，会发出警报，该设置去掉警报
set ruler              |                    "在编辑过程中，在右下角显示光标位置的状态行
set nohls       |                         "默认情况下，寻找匹配是高亮度显示，该设置关闭高亮显示
set incsearch   |                     "在程序中查询一单词，自动匹配单词的位置；如查询desk单词，当输到/d时，会自动找到第一个d开头的单词，当输入到/de时，会自动找到第一个以ds开头的单词，以此类推，进行查找；当找到要匹配的单词时，别忘记回车 
set backspace=2   |        " 设置退格键可用
设置外观 | |
set number             |         "显示行号 
set showtabline=0      |         "隐藏顶部标签栏"
set guioptions-=r      |        "隐藏右侧滚动条" 
set guioptions-=L       |        "隐藏左侧滚动条"
set guioptions-=b      |         "隐藏底部滚动条"
set cursorline         |         "突出显示当前行"
set cursorcolumn       |         "突出显示当前列"
set langmenu=zh_CN.UTF-8 |       "显示中文菜单
变成辅助 | |
syntax on              |             "开启语法高亮
set nowrap             |         "设置代码不折行"
set fileformat=unix    |         "设置以unix的格式保存文件"
set cindent             |        "设置C样式的缩进格式"
set tabstop=4           |        "一个 tab 显示出来是多少个空格，默认 8
set shiftwidth=4       |         "每一级缩进是多少个空格
set backspace+=indent,eol,start "set backspace& | 可以对其重置
set showmatch            |       "显示匹配的括号"
set scrolloff=5           |      "距离顶部和底部5行"
set laststatus=2          |      "命令行为两行"
" 其他杂项 | |
set mouse=a               |      "启用鼠标"
set selection=exclusive | 
set selectmode=mouse,key | 
set matchtime=5 | 
set ignorecase          |        "忽略大小写"
set incsearch |
set hlsearch           |         "高亮搜索项"
set noexpandtab         |        "不允许扩展table"
set whichwrap+=<,>,h,l |
set autoread |