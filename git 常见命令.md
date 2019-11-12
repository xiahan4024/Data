# git 常见命令

# 一：全局配置

```shell
## 全局配置
git config --global user.name "xiahan"
git config --global user.email "xiahan@163.com"

## 创建git仓库
git init
## 添加到本地仓库
git add .
## 提交
git commit -m "message"
## 创建远程地址
git remote add github https:XXX
git remote add gitee https:XXX
## 查看远程仓库
git remote
git remote -v
## 推送到远程仓库
git push gitee master
git push github master

```

