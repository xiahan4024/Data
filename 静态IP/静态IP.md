# 一：静态IP

1. 修改虚拟机为静态ip地址

   1. 修改下面配置

   ```shell
   vi /etc/sysconfig/network-scripts/ifcfg-ens33
   ##修改前
   TYPE=Ethernet
   PROXY_METHOD=none
   BROWSER_ONLY=no
   BOOTPROTO=dhcp
   DEFROUTE=yes
   IPV4_FAILURE_FATAL=no
   IPV6INIT=yes
   IPV6_AUTOCONF=yes
   IPV6_DEFROUTE=yes
   IPV6_FAILURE_FATAL=no
   IPV6_ADDR_GEN_MODE=stable-privacy
   NAME=ens33
   UUID=15dc7bd9-275d-407d-98da-a66e4ec26aee
   DEVICE=ens33
   ONBOOT=no
   ##修改后
   TYPE=Ethernet
   PROXY_METHOD=none
   BROWSER_ONLY=no
   BOOTPROTO=static   ## 静态ip
   DEFROUTE=yes
   IPV4_FAILURE_FATAL=no
   IPV6INIT=yes
   IPV6_AUTOCONF=yes
   IPV6_DEFROUTE=yes
   IPV6_FAILURE_FATAL=no
   IPV6_ADDR_GEN_MODE=stable-privacy
   NAME=ens33
   UUID=15dc7bd9-275d-407d-98da-a66e4ec26aee
   DEVICE=ens33
   ONBOOT=yes  ##开机启动
   IPADDR=192.168.110.100  ##ip地址
   NETMASK=255.255.255.0  ##子网掩码
   GATEWAY=192.168.110.2 ##默认网关 一般尾数为：2
   DNS1=192.168.110.2  ##DNS  一般尾数为：2
   
   ```

   2. 上面ip、子网掩码、默认网关等相关数字需要保持和vm设置一致。

      VM设置：编辑-->虚拟网络编辑-->NTA模式 

      ![NTA模式](./picture/NTA.png)

      

      ![NTA详细](./picture/NTA-2.png)

   3. 重新加载配置文件（修改后并没有立即生效，需要重新加载）

      ```shell
      service network restart
      Restarting network (via systemctl):                 [  OK  ]
      
      ```

   4. 测试

      ```shell
      [root@localhost ~]# ip addr
      1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
          link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
          inet 127.0.0.1/8 scope host lo
             valid_lft forever preferred_lft forever
          inet6 ::1/128 scope host 
             valid_lft forever preferred_lft forever
      2: ens33: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
          link/ether 00:0c:29:44:d7:d4 brd ff:ff:ff:ff:ff:ff
          inet 192.168.110.100/24 brd 192.168.110.255 scope global noprefixroute ens33
             valid_lft forever preferred_lft forever
          inet6 fe80::82f1:4bf9:b245:ba55/64 scope link noprefixroute 
             valid_lft forever preferred_lft forever
      [root@localhost ~]# ping www.baidu.com
      PING www.a.shifen.com (39.156.66.14) 56(84) bytes of data.
      64 bytes from 39.156.66.14 (39.156.66.14): icmp_seq=1 ttl=128 time=5.34 ms
      64 bytes from 39.156.66.14 (39.156.66.14): icmp_seq=2 ttl=128 time=5.05 ms
      64 bytes from 39.156.66.14 (39.156.66.14): icmp_seq=3 ttl=128 time=5.14 ms
      64 bytes from 39.156.66.14 (39.156.66.14): icmp_seq=4 ttl=128 time=4.45 ms
      64 bytes from 39.156.66.14 (39.156.66.14): icmp_seq=5 ttl=128 time=5.27 ms
      ^C
      --- www.a.shifen.com ping statistics ---
      5 packets transmitted, 5 received, 0% packet loss, time 4007ms
      rtt min/avg/max/mdev = 4.458/5.056/5.348/0.321 ms
      [root@localhost ~]# 
      
      ```

      

   5. 查看防火墙状态：Active: active (running)即为开启防火墙

      ```shell
      [root@localhost ~]# systemctl status firewalld
      ● firewalld.service - firewalld - dynamic firewall daemon
         Loaded: loaded (/usr/lib/systemd/system/firewalld.service; disabled; vendor preset: enabled)
         Active: active (running) since Mon 2019-10-21 07:07:57 CST; 1s ago
           Docs: man:firewalld(1)
       Main PID: 7433 (firewalld)
         CGroup: /system.slice/firewalld.service
                 └─7433 /usr/bin/python -Es /usr/sbin/firewalld --nofork --nopid
      
      Oct 21 07:07:57 localhost.localdomain systemd[1]: Starting firewalld - dynamic firewall daemon...
      Oct 21 07:07:57 localhost.localdomain systemd[1]: Started firewalld - dynamic firewall daemon.
      [root@localhost ~]# 
      
      
      ```

   6. 关闭防火墙：Active: inactive (dead)只是当前关闭，开机仍会启动防火墙

      ```shell
      [root@localhost ~]# systemctl stop firewalld
      [root@localhost ~]# systemctl status firewalld
      ● firewalld.service - firewalld - dynamic firewall daemon
         Loaded: loaded (/usr/lib/systemd/system/firewalld.service; disabled; vendor preset: enabled)
         Active: inactive (dead)
           Docs: man:firewalld(1)
      
      Oct 21 07:07:57 localhost.localdomain systemd[1]: Starting firewalld - dynamic firewall daemon...
      Oct 21 07:07:57 localhost.localdomain systemd[1]: Started firewalld - dynamic firewall daemon.
      Oct 21 07:10:19 localhost.localdomain systemd[1]: Stopping firewalld - dynamic firewall daemon...
      Oct 21 07:10:20 localhost.localdomain systemd[1]: Stopped firewalld - dynamic firewall daemon.
      [root@localhost ~]# 
      ```

   7. 关闭开机启动防火墙。重启检查防火墙状态

      ```shell
      [root@localhost ~]# systemctl disable firewalld
      
      ```

   8. 开放某一个发端口。**~~百度而来，并没有验证~~**

      ```shell
      firewall-cmd —list-ports  ##查看以开放端口命令
      firewall-cmd --zone=public --add-port=80/tcp --permanent  ## 开启端口
      命令含义：
      –zone #作用域
      –add-port=80/tcp #添加端口，格式为：端口/通讯协议
      –permanent #永久生效，没有此参数重启后失效
      
      #重启firewall  
      firewall-cmd --reload
      ```

    ## 9. 安装软件
   
      ```shell
   ## 更新yum
   [root@localhost ~]# yum update
   ## 安装 vim
   [root@localhost Backup]# yum install -y vim
   
   ## 安装 rsync
   [root@hadoop0-110 backup]# yum install -y rsync
   ## 查看状态
   [root@hadoop0-110 ~]# systemctl status rsyncd.service
      ```
   
      
   
   10. 开机多个选项删除
   
       ```shell
       [root@localhost ~]# uname -a  ##查看系统当前内核版本
       Linux localhost.localdomain 3.10.0-1062.1.2.el7.x86_64 #1 SMP Mon Sep 30 14:19:46 UTC 2019 x86_64 x86_64 x86_64 GNU/Linux
       [root@localhost ~]# rpm -q -a | grep kernel ## 查看系统中全部的内核RPM包
       kernel-3.10.0-1062.1.2.el7.x86_64
       kernel-3.10.0-957.el7.x86_64
       kernel-tools-3.10.0-1062.1.2.el7.x86_64
       kernel-tools-libs-3.10.0-1062.1.2.el7.x86_64
       [root@localhost ~]# yum remove kernel-3.10.0-957.el7.x86_64 ## 删除与内核不符合的版本
       ```
   
       
   
   11. 其他的后续再补充

# 二：JDK 安装

- 上传JDK1.8 到/opt/Backup

- tar -zxvf 解压JDK 到 /opt

  ```shell
  [root@localhost Backup]# tar -zxvf jdk-8u201-linux-x64.tar.gz -C /opt
  ```

- 配置环境变量

  ```shell
  [root@localhost Backup]# vim /etc/profile  ##里面：set nu开启行号
  
  ## 末尾添加如下
  ##JDK
  export JAVA_HOME=/opt/jdk1.8.0_201
  export CLASSPATH=$:CLASSPATH:$JAVA_HOME/lib/ 
  export PATH=$PATH:$JAVA_HOME/bin
  
  ##重启profile文件
  [root@localhost ~]# source /etc/profile
  
  ##验证JDK
  [root@localhost Backup]# java -version
  java version "1.8.0_201"
  Java(TM) SE Runtime Environment (build 1.8.0_201-b09)
  Java HotSpot(TM) 64-Bit Server VM (build 25.201-b09, mixed mode)
  [root@localhost Backup]# 
  ```

# 三：docker安装

  ## 1. YUM安装docker

```shell
[root@localhost ~]# yum install docker -y

##验证docker是否安装
[root@localhost ~]# docker version
Client:
 Version:         1.13.1
 API version:     1.26
 Package version: 
Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
[root@localhost ~]# 

```

## 2. 启动docker

```shell
systemctl start docker  #启动docker
systemctl enable docker #开机启动docker
systemctl status docker #查看docker状态

```

## 3. 修改docker源

```shell
[root@localhost ~]# vim /etc/docker/daemon.json

##添加：DaoCloud 加速器  （百度来的，不知道啥玩意测试还挺不错的）
{
    "registry-mirrors": [
        "http://95822026.m.daocloud.io"
    ],
    "insecure-registries": []
}

## 阿里源
{
  "registry-mirrors": ["https://72idtxd8.mirror.aliyuncs.com"]
}


##修改后
systemctl daemon-reload  ## 重启daemon
systemctl  restart docker  ##重启docker服务；
```

# 四：docker常用命令

```shell
docker version ##查看docker版本

systemctl status docker ## 查看docker运行状态
systemctl start docker ## 启动docker
systemctl stop docker ## 停止docker
systemctl enable docker ## 开机启动docker，并不会立即启动docker

docker images ## 查看镜像
docker search redis ## 查看redis
docker pull redis ## 拉取redis
docker rmi <image id> ## 删除镜像
docker ps ##查看运行中的容器
docker ps -a ## 查看所有的容器，包括停止

docker run ## 启动一个新容器
docker start <CONTAINER ID> ##启动某个停止运行的容器
docker stop <CONTAINER ID> ##停止容器
docker rm <CONTAINER ID> ## 删除容器
docker ps -q ##查看运行中的 CONTAINER ID
docker ps -a -q ## 查看所有的 CONTAINER ID
docker stop $(docker ps -a -q) ## 停止所有的容器

docker logs <CONTAINER ID> ##查看docker容器运行日志

##mysql为例
docker run -p 3306:3306 --name mysql -v /opt/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=xiahan -d --privileged=true mysql:5.7

##解释命令
-p 3306:3306：将容器3306映射到主机3306
--name mysql：容器取名
-v /opt/mysql/data:/var/lib/mysql：将主机的/opt/mysql/data挂载到容器/var/lib/mysql中
-e MYSQL_ROOT_PASSWORD=xiahan：root用户密码
-d：后台启动
--privileged=true：提高权限文件挂载的时候报错可以添加这个。据说有危险性
--restart=always：开机就启动
mysql:5.7：启动的是哪个mysql(5.7是这次的版本)


```



# 五：docker安装mysql 5.7

```shell
[root@localhost ~]# docker search mysql  ##查询mysql
[root@localhost ~]# docker pull mysql:5.7  ##拉取mysql 5.7
[root@localhost ~]# docker images  ##查询本地下载所有镜像
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
docker.io/mysql     5.7                 cd3ed0dfff7e        4 days ago          437 MB

 docker run --name mysql -p 3306:3306 -v /mysql/datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456  -d mysql
 
 # 建立挂载的数据卷
mkdir -p /opt/mysql/data

# 虚拟机 3306 端口和容器里的3306端口对应，虚拟机路径和容器里路径（下图，来自头部参考地址）对应
docker run -p 3306:3306 --name mysql --restart=always -v /opt/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=xiahan -d --privileged=true mysql:5.7

##解释命令
-p 3306:3306：将容器3306映射到主机3306
--name mysql：容器取名
-v /opt/mysql/data:/var/lib/mysql：将主机的/opt/mysql/data挂载到容器/var/lib/mysql中
-e MYSQL_ROOT_PASSWORD=xiahan：root用户密码
-d：后台启动
--privileged=true：提高权限文件挂载的时候报错可以添加这个。据说有危险性
--restart=always：开机就启动
mysql:5.7：启动的是哪个mysql(5.7是这次的版本)

```



# 六：docker安装 Redis

## 1. 命令
```shell
[root@localhost ~]# docker search redis
[root@localhost ~]# docker pull redis
[root@localhost ~]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
docker.io/mysql     5.7                 cd3ed0dfff7e        2 weeks ago         437 MB
docker.io/redis     latest              de25a81a5a0b        2 weeks ago         98.2 MB
[root@localhost ~]# docker run -p 6379:6379 -v /opt/redis/redis.conf:/etc/redis/redis.conf -v /opt/redis/redis-6379:/data --privileged=true --name redis_6379 -d redis redis-server /etc/redis/redis.conf --appendonly yes --requirepass root

不开机启动：
[root@localhost ~]# docker run -p 6379:6379 -v /opt/redis/redis.conf:/etc/redis/redis.conf -v /opt/redis/redis-6379:/data --privileged=true --name redis_6379 --restart=always -d redis redis-server /etc/redis/redis.conf --appendonly yes --requirepass root
## --appendonly yes 持久化
## --requirepass root 密码为:root
## -p 6379:6379  端口映射
## -v /opt/redis/redis.conf:/etc/redis/redis.conf		配置文件挂载
## -v /opt/redis/redis-6379:/data		数据文件夹挂载
## --privileged=true		提升权限
## --name redis_6379		别名
## -d redis		后台启动
## redis-server /etc/redis/redis.conf		以配置文件启动
## --restart=always		开机启动
## -d redis redis-server /etc/redis/redis.conf  -d redis在/etc/redis/redis.conf前面，在后面可能会出错
```
## 2. redis.conf文件修改

这是使用的是redis-5.0.5.tar.gz版本的redis里面的config配置文件

```conf
1. bind 127.0.0.1  		70行左右，注释掉
2. protected-mode yes     90行左右，改为：protected-mode no
	开启了 protected-mode 时，如果你既没有显示的定义了 bind,监听的地址，同时又没有设置 auth 密码。那你只能通过 127.0.0.1 来访问 redis 服务
3. appendonly no         702行左右 持久化改为：appendonly yes
4. #requirepass foobared   510行左右，设置redis密码
5. daemonize:yes		redis后台运行，改为no，退出界面就kill掉进程
```
## 3. tar.gz安装redis

```shell
参考秒杀项目第一天的安装redis

后面有时间补齐这个文档
```



# 七：rabbitMQ安装

```shell
不带web页面
[root@localhost ~]# docker pull rabbitmq
[root@localhost ~]# docker run -p 5672:5672 -p 15672:15672 -d --name rabbitmq --privileged=true -v /opt/rabbitmq/data:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq

带web页面：
[root@localhost ~]# docker pull rabbitmq:management
[root@localhost ~]# docker run -p 5672:5672 -p 15672:15672 -d --name rabbitmq --privileged=true -v /opt/rabbitmq/data:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:management

##命令解说
-d 后台运行容器；

--name 指定容器名；

-p 指定服务运行的端口（5672：应用访问端口；15672：控制台Web端口号）；

-v 映射目录或文件；

--hostname  主机名（RabbitMQ的一个重要注意事项是它根据所谓的 “节点名称” 存储数据，默认为主机名）；

-e 指定环境变量；（RABBITMQ_DEFAULT_VHOST：默认虚拟机名；RABBITMQ_DEFAULT_USER：默认的用户名；RABBITMQ_DEFAULT_PASS：默认用户名的密码）

```

# 八： contos 常见命令

```shell
## 修改背景
cat /etc/motd

## top 监控linux的性能，cpu、内存
top

## 运行 springboot 命令
nohup java -jar xxx.jar &

## 修改使用者和使用者组
语法：chown [-cfhvR] [--help] [--version] user[:group] file...
chown runoob:runoobgroup file1 -R ## 将文件 file1.txt 的拥有者设为 runoob，群体的使用者 runoobgroup : -r 递归文件

```



# 九： 安装hadoop

## 1. 解压 hadoop

```shell
## 解压
[root@localhost opt]# tar -zxvf /opt/backup/hadoop-2.7.2.tar.gz -C /opt/
## 获取路径
[root@localhost hadoop-2.7.2]# pwd
/opt/hadoop-2.7.2
## 修改环境变量
[root@localhost jdk1.8.0_201]# vim /etc/profile
## 追加
## hadoop
export HADOOP_HOME=/opt/hadoop-2.7.2
export PATH=$PATH:$HADOOP_HOME/bin
export PATH=$PATH:$HADOOP_HOME/sbin
## 配置立即生效
[root@localhost jdk1.8.0_201]# source /etc/profile
## 查看 hadoop 是否安装成功
[root@localhost jdk1.8.0_201]# hadoop version
Hadoop 2.7.2
Subversion https://git-wip-us.apache.org/repos/asf/hadoop.git -r b165c4fe8a74265c792ce23f546c64604acf0e41
Compiled by jenkins on 2016-01-26T00:08Z
Compiled with protoc 2.5.0
From source with checksum d0fda26633fa762bff87ec759ebe689c
This command was run using /opt/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar
## 如果不能用 重启试试
[root@localhost jdk1.8.0_201]# reboot


```

## 2. 克隆 Linux

![](./picture/hadoop/copy1.png)

![](./picture/hadoop/copy2.png)



```shell
## 修改相关配置
## 获取uuid
[root@localhost ~]# uuidgen ens33
5b8bba98-3972-4f85-9937-c32771901823
[root@localhost rules.d]# vim /etc/sysconfig/network-scripts/ifcfg-ens33
UUID=5b8bba98-3972-4f85-9937-c32771901823  ## 修改之前生成的uuid
IPADDR=192.168.110.111  ## 修改ip地址

## 修改主机名
[root@localhost rules.d]# vim /etc/hostname 
## 修改为
hadoop1-111

## hots 追加
[root@xiahan ~]# vim /etc/hosts
192.168.110.110 hadoop0-110
192.168.110.111 hadoop1-111
192.168.110.112 hadoop2-112

```



## 3. 集群分发脚本

### 1）scp: 安全拷贝	

```shell
scp    -r          $pdir/$fname              $user@hadoop$host:$pdir/$fname

命令   递归       要拷贝的文件路径/名称    目的用户@主机:目的路径/名称

scp -r /opt/module  root@hadoop102:/opt/module

## 反过来，在112上拷贝111的文件也行
[root@hadoop1-111 opt]# scp -r root@hadoop0-110:/opt/backup /opt/backup/
## 中间者，在111上拷贝110的文件到112上
[root@hadoop1-111 backup]# scp -r root@hadoop0-110:/opt/backup/temp root@hadoop2-112:/opt/backup/

```

### 2) 	rsync   :         远程同步工具  

>rsync主要用于备份和镜像。具有速度快、避免复制相同内容和支持符号链接的优点。
>
>rsync和scp区别：用rsync做文件的复制要比scp的速度快，rsync只对差异文件做更新。scp是把所有文件都复制过去。

（1）基本语法

>rsync   -rvl    $pdir/$fname        $user@hadoop$host:$pdir/$fname
>
>命令  选项参数  要拷贝的文件路径/名称  目的用户@主机:目的路径/名称

选项参数说明
| 选项 | 功能         |
| ---- | ------------ |
| -r   | 递归         |
| -v   | 显示复制过程 |
| -l   | 拷贝符号连接 |

```shell
[root@hadoop0-110 backup]# rsync temp root@hadoop1-111:/opt/backup/
```

### 3）群发脚本

```shell
## 创建文件
[root@hadoop0-110 ~]# mkdir bin
[root@hadoop0-110 ~]# cd bin/
[root@hadoop0-110 bin]# touch xsync
[root@hadoop0-110 bin]# vim xsync
## 修改权限
[root@hadoop0-110 bin]# chmod 777 xsync
[root@hadoop0-110 bin]# ll
total 4
-rwxrwxrwx. 1 root root 514 Nov 29 05:12 xsync

## 使用
[root@hadoop0-110 ~]# xsync bin/ 
fname=bin
pdir=/root
------------------- hadoop111 --------------
root@hadoop1-111's password: 
sending incremental file list
bin/
bin/xsync

sent 638 bytes  received 39 bytes  150.44 bytes/sec
total size is 514  speedup is 0.76
------------------- hadoop112 --------------
root@hadoop2-112's password: 
sending incremental file list
bin/
bin/xsync

sent 638 bytes  received 39 bytes  270.80 bytes/sec
total size is 514  speedup is 0.76


## xsync 脚本

#!/bin/bash
#1 获取输入参数个数，如果没有参数，直接退出
pcount=$#
if((pcount==0)); then
echo no args;
exit;
fi

#2 获取文件名称
p1=$1
fname=`basename $p1`
echo fname=$fname

#3 获取上级目录到绝对路径
pdir=`cd -P $(dirname $p1); pwd`
echo pdir=$pdir

#4 获取当前用户名称
user=`whoami`

#5 循环
for((host=111, i =1; host<113; host++, i++)); do
        echo ------------------- hadoop$host --------------
        rsync -rvl $pdir/$fname $user@hadoop$i-$host:$pdir
done

```

>如果部分不能全局使用 xsync，输入：echo $PATH
>
>
>
>[root@hadoop1-111 bin]# echo $PATH
>/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/opt/jdk1.8.0_201/bin:/opt/hadoop-2.7.2/bin:/opt/hadoop-2.7.2/sbin:/root/bin
>
>将 xsync 移动到上面的一个文件夹即可使用





