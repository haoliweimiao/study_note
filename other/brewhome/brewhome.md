# Brewhome

## 更换阿里源

~~~ shell
# 替换brew.git:
cd "$(brew --repo)"
git remote set-url origin https://mirrors.aliyun.com/homebrew/brew.git
# 替换homebrew-core.git:
cd "$(brew --repo)/Library/Taps/homebrew/homebrew-core"
git remote set-url origin https://mirrors.aliyun.com/homebrew/homebrew-core.git
# 应用生效
brew update
# 替换homebrew-bottles:
echo 'export HOMEBREW_BOTTLE_DOMAIN=https://mirrors.aliyun.com/homebrew/homebrew-bottles' >> ~/.zshrc
source ~/.zshrc
~~~

## 换回默认源
~~~ shell
# 重置brew.git:#HTTP代理
proxy=127.0.0.1:1080
cd "$(brew --repo)"
git remote set-url origin https://github.com/Homebrew/brew.git
# 重置homebrew-core.git:
cd "$(brew --repo)/Library/Taps/homebrew/homebrew-core"
git remote set-url origin https://github.com/Homebrew/homebrew-core.git

#删除zshrc HOMEBREW_BOTTLE_DOMAIN内容
~~~