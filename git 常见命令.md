# git 常见命令

# 一：全局配置

```shell
## 全局配置
$ git config --global user.name "xiahan"
$ git config --global user.email "xiahan@163.com"

## 创建git仓库
$ git init
## 添加到本地仓库
$ git add .
## 提交
$ git commit -m "message"
## 创建远程地址
$ git remote add github https:XXX
$ git remote add gitee https:XXX
## 查看远程仓库
$ git remote
$ git remote -v
## 推送到远程仓库
$ git push gitee master
$ git push github master
## 查看和git版本库有什么不同，可以查看修改了什么内容，查看difference
$ git diff
## 查看提交日志
$ git log
$ git log --pretty=oneline
$ git reflog --pretty=oneline
## 克隆远程
$ git clone https://github.com/xiahan4024/temp.git
## 版本回退
$ git reset --hard 84cqw
## 创建分支并切换到分支
$ git checkout -b dev
## git checkout命令加上-b参数表示创建并切换，相当于以下两条命令
$ git branch dev  ## 创建
$ git checkout dev ## 切换
Switched to branch 'dev'
## 用git branch命令查看当前分支
$ git branch
* dev
  master
## 切换回master分支
$ git checkout master
Switched to branch 'master'
## 把dev分支的工作成果合并到master分支上
$ git merge dev
Updating d46f35e..b17d20e
Fast-forward
 readme.txt | 1 +
 1 file changed, 1 insertion(+)
 ## 合并完成后，就可以放心地删除dev分支
 $ git branch -d dev
Deleted branch dev (was b17d20e).
## 删除后，查看branch，就只剩下master分支
$ git branch
* master
## 新版本的Git提供了新的git switch命令来切换分支
## 创建并切换到新的dev分支
$ git switch -c dev
## 直接切换到已有的master分支
$ git switch master


## git ssh 免密登录
## 1.进入git bash 使用以下命令，接着连续敲三次回车。git 账号邮箱。
$ ssh-keygen -t rsa -C "email@email.com"
## 2.查看你生成的公钥：
$ cat ~/.ssh/id_rsa.pub
## 3.登陆你的github帐户。点击你的头像，然后 Settings -> 左栏点击 SSH and GPG keys -> 点击 New SSH key新建公钥title可以随便输key就是你刚刚新建的公钥
## 4.测试：
$ ssh git@github.com
$ ssh -T git@gitee.com
```

