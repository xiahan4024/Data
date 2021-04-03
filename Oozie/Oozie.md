# 一： Hadoop CDH 版本安装

## 1. 上传、解压 cdh hadoop版本

```shell
[root@hadoop0-110 cdh]# pwd
/opt/backup/cdh
[root@hadoop0-110 cdh]# ll
total 1304756
-rw-r--r--. 1 root root    3759787 Nov 21 18:45 cdh5.3.6-snappy-lib-natirve.tar.gz
-rw-r--r--. 1 root root    6800612 Nov 21 18:45 ext-2.2.zip
-rw-r--r--. 1 root root  293471952 Nov 21 18:45 hadoop-2.5.0-cdh5.3.6.tar.gz
-rw-r--r--. 1 root root 1032028646 Nov 21 18:45 oozie-4.0.0-cdh5.3.6.tar.gz
## 解压 hadoop
[root@hadoop0-110 cdh]# tar -axvf hadoop-2.5.0-cdh5.3.6.tar.gz -C /opt/cdh/
## 解压 oozie
[root@hadoop0-110 cdh]# tar -zxvf oozie-4.0.0-cdh5.3.6.tar.gz -C /opt/cdh/
```

## 2. 修改hadoop 配置文件

```shell
## 一共修改八个配置文件：
## 三个 env ：hadoop-env.sh、mapred-env.sh、yarn-env.sh
## 四个site：core-site.xml、hdfs-site.xml、mapred-site.xml.template、yarn-site.xml
## 一个slaves：slaves
```

```shell
## 三个 env。全都是配置一个 java_home
## export JAVA_HOME=/opt/jdk1.8.0_201
[root@hadoop0-110 hadoop]# vim hadoop-env.sh
[root@hadoop0-110 hadoop]# vim mapred-env.sh
[root@hadoop0-110 hadoop]# vim yarn-env.sh
```

```shell
## 四个site

## 查看用户所在组
[root@hadoop1-111 ~]# groups root
root : root

## core-site.xml  修改 namenode 端口号
<configuration>
        <!-- 指定HDFS中NameNode的地址 -->
        <property>
                <name>fs.defaultFS</name>
                <value>hdfs://hadoop0-110:8020</value>
        </property>

        <!-- 指定Hadoop运行时产生文件的存储目录 -->
        <property>
                <name>hadoop.tmp.dir</name>
                <value>/opt/cdh/hadoop-2.5.0-cdh5.3.6/data</value>
        </property>
        <!-- Oozie Server的Hostname -->
        <property>
            <name>hadoop.proxyuser.[Oozie_server_user].hosts</name>
            <value>[oozie_server_hostname]</value> ## 可用* 代替
        </property>

        <!-- 允许被Oozie代理的用户组 -->
        <property>
            <name>hadoop.proxyuser.[Oozie_server_user].groups</name>
            <value>[Oozie_groups_that_allow_impersonation]</value>## 可用* 代替
        </property>

</configuration>

## hdfs-site.xml
<configuration>
	<!-- 指定HDFS副本的数量 -->
	<property>
		<name>dfs.replication</name>
		<value>1</value>
	</property>

	<!-- 指定Hadoop辅助名称节点主机配置 -->
	<property>
	      <name>dfs.namenode.secondary.http-address</name>
	      <value>hadoop2-112:50090</value>
	</property>
</configuration>

## mapred-site.xml
<configuration>
	<!-- 指定MR运行在YARN上 -->
	<property>
		<name>mapreduce.framework.name</name>
		<value>yarn</value>
	</property>

	<!-- 历史服务器端地址 -->
	<property>
		<name>mapreduce.jobhistory.address</name>
		<value>hadoop0-110:10020</value>
	</property>
	<!-- 历史服务器web端地址 -->
	<property>
		<name>mapreduce.jobhistory.webapp.address</name>
		<value>hadoop0-110:19888</value>
	</property>

</configuration>

## yarn-site.xml
<configuration>
	<!-- Reducer获取数据的方式 -->
	<property>
		<name>yarn.nodemanager.aux-services</name>
 		<value>mapreduce_shuffle</value>
	</property>

	<!-- 指定YARN的ResourceManager的地址 -->
	<property>
		<name>yarn.resourcemanager.hostname</name>
		<value>hadoop1-111</value>
	</property>

	<!-- 日志聚集功能使能 -->
	<property>
		<name>yarn.log-aggregation-enable</name>
		<value>true</value>
	</property>

	<!-- 日志保留时间设置7天 , 单位 秒-->
	<property>
		<name>yarn.log-aggregation.retain-seconds</name>
		<value>604800</value>
	</property>
</configuration>

```

## 3. 启动
```shell
## 格式化 datanode
[root@hadoop0-110 hadoop-2.5.0-cdh5.3.6]# bin/hdfs namenode -format
## NameNode在 hadoop0-110 所以在这上面启动HDFS
[root@hadoop0-110 hadoop-2.5.0-cdh5.3.6]# sbin/start-dfs.sh 
## NameNode在 hadoop1-111 所以在这上面启动YARN
[root@hadoop0-110 hadoop-2.5.0-cdh5.3.6]# sbin/start-yarn.sh 
## 启动history
[root@hadoop0-110 hadoop-2.5.0-cdh5.3.6]# sbin/mr-jobhistory-daemon.sh start historyserver

## 停止
## NameNode在 hadoop0-110 所以在这上面停止HDFS
[root@hadoop0-110 hadoop-2.5.0-cdh5.3.6]# sbin/stop-dfs.sh 
## NameNode在 hadoop1-111 所以在这上面停止YARN
[root@hadoop0-110 hadoop-2.5.0-cdh5.3.6]# sbin/stop-yarn.sh 
## 停止history
[root@hadoop0-110 hadoop-2.5.0-cdh5.3.6]# sbin/mr-jobhistory-daemon.sh stop historyserver
```

# 二：Oozie安装

## 1. 上传、解压 cdh版

```shell
## 解压 oozie
[root@hadoop0-110 cdh]# tar -zxvf oozie-4.0.0-cdh5.3.6.tar.gz -C /opt/cdh/
## 解压oozie-hadooplibs-4.0.0-cdh5.3.6.tar.gz 到上层
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# pwd
/opt/cdh/oozie-4.0.0-cdh5.3.6
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# tar -zxvf oozie-hadooplibs-4.0.0-cdh5.3.6.tar.gz -C ../
## 创建文件夹：libext
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# mkdir libext
## 复制文件到 libext中
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# cp hadooplibs/hadooplib-2.5.0-cdh5.3.6.oozie-4.0.0-cdh5.3.6/* ./libext/
## 复制 ext.zip到 libext文件夹下
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# cp /opt/backup/cdh/ext-2.2.zip ./libext/
## 复制mysql驱动包到 libext文件夹
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# cd /opt/mysql-libs/mysql-connector-java-5.1.27/mysql-connector-java-5.1.27-bin.jar ./libext/
```

## 2. 修改Oozie 配置文件

```shell
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# cd conf/
[root@hadoop0-110 conf]# vim oozie-site.xml
属性：oozie.service.JPAService.jdbc.driver
属性值：com.mysql.jdbc.Driver
解释：JDBC的驱动

属性：oozie.service.JPAService.jdbc.url
属性值：jdbc:mysql://hadoop0-110:3306/oozie
解释：oozie所需的数据库地址

属性：oozie.service.JPAService.jdbc.username
属性值：root
解释：数据库用户名

属性：oozie.service.JPAService.jdbc.password
属性值：xiahan
解释：数据库密码

属性：oozie.service.HadoopAccessorService.hadoop.configurations
属性值：*=/opt/cdh/hadoop-2.5.0-cdh5.3.6/etc/hadoop
解释：让Oozie引用Hadoop的配置文件

```

## 3. 发布、启动OOzie

```shell
## 上传Oozie目录下的yarn.tar.gz文件到HDFS：
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# pwd
/opt/cdh/oozie-4.0.0-cdh5.3.6
## 执行成功之后，去50070检查对应目录有没有文件生成。
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# bin/oozie-setup.sh sharelib create -fs hdfs://hadoop0-110:8020 -locallib oozie-sharelib-4.0.0-cdh5.3.6-yarn.tar.gz
## 创建oozie.sql文件
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# bin/ooziedb.sh create -sqlfile oozie.sql -run
## 打包项目，生成war包
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# bin/oozie-setup.sh prepare-war


## Oozie的启动与关闭
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# bin/oozied.sh start
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# bin/oozied.sh stop

## 访问页面
http://hadoop0-110:11000/oozie
```

![](./pic/1605973333(1).png)

## 4. 调度shell 脚本

```shell
## 解压附带的examples
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# pwd
/opt/cdh/oozie-4.0.0-cdh5.3.6
[root@hadoop0-110 oozie-4.0.0-cdh5.3.6]# tar -zxvf oozie-examples.tar.gz
## 创建文件夹存放job
[root@hadoop1-111 oozie-4.0.0-cdh5.3.6]# mkdir oozie-apps
## 复制实例shell到创建的文件夹
[root@hadoop1-111 oozie-4.0.0-cdh5.3.6]# cp -r ./examples/apps/shell ./oozie-apps/
## 创建 p1.sh
[root@hadoop1-111 oozie-4.0.0-cdh5.3.6]# cd oozie-apps/shell/
[root@hadoop1-111 shell]# vim p1.sh
[root@hadoop1-111 shell]# cat p1.sh 
#!/bin/bash
/sbin/date > opt/p1.log
[root@hadoop1-111 shell]# cat job.properties 
## nameNode 所在位置
nameNode=hdfs://hadoop0-110:8020  
## yarn/resourcemanager 所在位置
jobTracker=hadoop1-111:8032
queueName=default
## 主要是和下面的路径对应起来
examplesRoot=oozie-apps
oozie.wf.application.path=${nameNode}/user/${user.name}/${examplesRoot}/shell
## 后面workflow.xml 文件会用到
EXEC=p1.sh

## workflow.xml修改
[root@hadoop1-111 shell]# cat workflow.xml
<workflow-app xmlns="uri:oozie:workflow:0.4" name="shell-wf">
    <start to="shell-node"/>
    <action name="shell-node">
        <shell xmlns="uri:oozie:shell-action:0.2">
            <job-tracker>${jobTracker}</job-tracker>
            <name-node>${nameNode}</name-node>
            <configuration>
                <property>
                    <name>mapred.job.queue.name</name>
                    <value>${queueName}</value>
                </property>
            </configuration>
            <exec>${EXEC}</exec>
        	<!-- 
        	<exec>echo</exec>
            <argument>my_output=Hello Oozie</argument>
            -->
        	<file>/user/root/oozie-apps/shell/${EXEC}#${EXEC}</file>
            <capture-output/>
        </shell>
        <ok to="end"/>
        <error to="fail"/>
    </action>
    <kill name="fail">
        <message>Shell action failed, error message[${wf:errorMessage(wf:lastErrorNode())}]</message>
    </kill>
    <end name="end"/>
</workflow-app>

## 上传文件到HDFS 上
[root@hadoop1-111 shell]# /opt/cdh/hadoop-2.5.0-cdh5.3.6/bin/hadoop fs -put /opt/cdh/oozie-4.0.0-cdh5.3.6/oozie-apps /user/root
## 执行任务
bin/oozie job -oozie http://hadoop102:11000/oozie -config oozie-apps/shell/job.properties -run
[root@hadoop1-111 oozie-4.0.0-cdh5.3.6]# bin/oozie job -oozie http://hadoop0-110:11000/oozie -config oozie-apps/shell/job.properties -run
```

![](./pic/1606226064(1).png)

## 5. 调度多个任务

```shell
## 1）	解压官方案例模板
[root@hadoop1-111 oozie-4.0.0-cdh5.3.6]# tar -zxf oozie-examples.tar.gz
## 2）	编写脚本
[root@hadoop1-111 oozie-4.0.0-cdh5.3.6]# vim oozie-apps/shell/p2.sh
#!/bin/bash
/bin/date > /opt/module/p2.log
## 3）修改job.properties和workflow.xml文件
	## job.properties
	nameNode=hdfs://hadoop0-110:8020
    jobTracker=hadoop1-110:8032
    queueName=default
    examplesRoot=oozie-apps
    oozie.wf.application.path=${nameNode}/user/${user.name}/${examplesRoot}/shell
    EXEC1=p1.sh
    EXEC2=p2.sh
    ## workflow.xml
	    <workflow-app xmlns="uri:oozie:workflow:0.4" name="shell-wf">
        <start to="p1-shell-node"/>
        <action name="p1-shell-node">
            <shell xmlns="uri:oozie:shell-action:0.2">
                <job-tracker>${jobTracker}</job-tracker>
                <name-node>${nameNode}</name-node>
                <configuration>
                    <property>
                        <name>mapred.job.queue.name</name>
                        <value>${queueName}</value>
                    </property>
                </configuration>
                <exec>${EXEC1}</exec>
                <file>/user/atguigu/oozie-apps/shell/${EXEC1}#${EXEC1}</file>
                <!-- <argument>my_output=Hello Oozie</argument>-->
                <capture-output/>
            </shell>
            <ok to="p2-shell-node"/>
            <error to="fail"/>
        </action>

        <action name="p2-shell-node">
            <shell xmlns="uri:oozie:shell-action:0.2">
                <job-tracker>${jobTracker}</job-tracker>
                <name-node>${nameNode}</name-node>
                <configuration>
                    <property>
                        <name>mapred.job.queue.name</name>
                        <value>${queueName}</value>
                    </property>
                </configuration>
                <exec>${EXEC2}</exec>
                <file>/user/admin/oozie-apps/shell/${EXEC2}#${EXEC2}</file>
                <!-- <argument>my_output=Hello Oozie</argument>-->
                <capture-output/>
            </shell>
            <ok to="end"/>
            <error to="fail"/>
        </action>
        <decision name="check-output">
            <switch>
                <case to="end">
                    ${wf:actionData('shell-node')['my_output'] eq 'Hello Oozie'}
                </case>
                <default to="fail-output"/>
            </switch>
        </decision>
        <kill name="fail">
            <message>Shell action failed, error message[${wf:errorMessage(wf:lastErrorNode())}]</message>
        </kill>
        <kill name="fail-output">
            <message>Incorrect output, expected [Hello Oozie] but was [${wf:actionData('shell-node')['my_output']}]</message>
        </kill>
        <end name="end"/>
    </workflow-app>
    ## 3）	上传任务配置
    $ bin/hadoop fs -rmr /user/root/oozie-apps/
	$ bin/hadoop fs -put oozie-apps/map-reduce /user/root/oozie-apps
	## 4）	执行任务
	[atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ bin/oozie job -oozie http://hadoop102:11000/oozie -config oozie-apps/shell/job.properties -run
```

## 6. 调度MapReduce任务

```shell
## 1.找到一个可以运行的mapreduce任务的jar包（可以用官方的，也可以是自己写的)
## 2.拷贝官方模板到oozie-apps
[atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$  cp -r /opt/module/cdh/ oozie-4.0.0-cdh5.3.6/examples/apps/map-reduce/ oozie-apps/
## 3.测试一下wordcount在yarn中的运行
[atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ /opt/module/cdh/hadoop-2.5.0-cdh5.3.6/bin/yarn jar /opt/module/cdh/hadoop-2.5.0-cdh5.3.6/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.5.0-cdh5.3.6.jar wordcount /input/ /output/
## 4.配置map-reduce任务的job.properties以及workflow.xml
	## job.properties
	nameNode=hdfs://hadoop102:8020
    jobTracker=hadoop103:8032
    queueName=default
    examplesRoot=oozie-apps
    #hdfs://hadoop102:8020/user/admin/oozie-apps/map-reduce/workflow.xml
    oozie.wf.application.path=${nameNode}/user/${user.name}/${examplesRoot}/map-reduce/workflow.xml
    outputDir=map-reduce
    ## workflow.xml
    <workflow-app xmlns="uri:oozie:workflow:0.2" name="map-reduce-wf">
        <start to="mr-node"/>
        <action name="mr-node">
            <map-reduce>
                <job-tracker>${jobTracker}</job-tracker>
                <name-node>${nameNode}</name-node>
                <prepare>
                    <delete path="${nameNode}/output/"/>
                </prepare>
                <configuration>
                    <property>
                        <name>mapred.job.queue.name</name>
                        <value>${queueName}</value>
                    </property>
                    <!-- 配置调度MR任务时，使用新的API -->
                    <property>
                        <name>mapred.mapper.new-api</name>
                        <value>true</value>
                    </property>

                    <property>
                        <name>mapred.reducer.new-api</name>
                        <value>true</value>
                    </property>

                    <!-- 指定Job Key输出类型 -->
                    <property>
                        <name>mapreduce.job.output.key.class</name>
                        <value>org.apache.hadoop.io.Text</value>
                    </property>

                    <!-- 指定Job Value输出类型 -->
                    <property>
                        <name>mapreduce.job.output.value.class</name>
                        <value>org.apache.hadoop.io.IntWritable</value>
                    </property>

                    <!-- 指定输入路径 -->
                    <property>
                        <name>mapred.input.dir</name>
                        <value>/input/</value>
                    </property>

                    <!-- 指定输出路径 -->
                    <property>
                        <name>mapred.output.dir</name>
                        <value>/output/</value>
                    </property>

                    <!-- 指定Map类 -->
                    <property>
                        <name>mapreduce.job.map.class</name>
                        <value>org.apache.hadoop.examples.WordCount$TokenizerMapper</value>
                    </property>

                    <!-- 指定Reduce类 -->
                    <property>
                        <name>mapreduce.job.reduce.class</name>
                        <value>org.apache.hadoop.examples.WordCount$IntSumReducer</value>
                    </property>

                    <property>
                        <name>mapred.map.tasks</name>
                        <value>1</value>
                    </property>
                </configuration>
            </map-reduce>
            <ok to="end"/>
            <error to="fail"/>
        </action>
        <kill name="fail">
            <message>Map/Reduce failed, error message[${wf:errorMessage(wf:lastErrorNode())}]</message>
        </kill>
        <end name="end"/>
	</workflow-app>
	## 拷贝待执行的jar包到map-reduce的lib目录下
	[atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ cp -a  /opt /module/cdh/hadoop-2.5.0-cdh5.3.6/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.5.0-cdh5.3.6.jar oozie-apps/map-reduce/lib
	## 上传配置好的app文件夹到HDFS
	[atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ /opt/module/cdh/hadoop-2.5.0-cdh5.3.6/bin/hdfs dfs -put oozie-apps/map-reduce/ /user/admin/oozie-apps
	## 执行任务
	[atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ bin/oozie job -oozie http://hadoop102:11000/oozie -config oozie-apps/map-reduce/job.properties -run
```

## 7.Oozie定时任务/循环任务

```shell
## 1.配置Linux时区以及时间服务器
## 2.检查系统当前时区：
[root@hadoop0-110 ~]# date -R
Mon, 30 Nov 2020 20:17:53 +0800
	## 注意：如果显示的时区不是+0800，删除localtime文件夹后，再关联一个正确时区的链接过去，命令如下：
	[root@hadoop0-110 ~]# rm -rf /etc/localtime
	[root@hadoop0-110 ~]# ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
	## 同步时间：
	[root@hadoop0-110 ~]# ntpdate pool.ntp.org
	## 修改NTP配置文件：
	# vi /etc/ntp.conf
        ## 去掉下面这行前面的# ,并把网段修改成自己的网段：
        restrict 192.168.122.0 mask 255.255.255.0 nomodify notrap
        ## 注释掉以下几行：
        #server 0.centos.pool.ntp.org
        #server 1.centos.pool.ntp.org
        #server 2.centos.pool.ntp.org
        ## 把下面两行前面的#号去掉,如果没有这两行内容,需要手动添加
        server  127.127.1.0    # local clock
        fudge  127.127.1.0 stratum 10
    ## 重启NTP服务：
        [root@hadoop0-110 ~]# systemctl start ntpd.service，
        ## 注意，如果是centOS7以下的版本，使用命令：service ntpd start
        [root@hadoop0-110 ~]# systemctl enable ntpd.service，
        ## 注意，如果是centOS7以下的版本，使用命令：chkconfig ntpd on
	## 集群其他节点去同步这台时间服务器时间：
	## 首先需要关闭这两台计算机的ntp服务
	[root@hadoop0-110 ~]# systemctl stop ntpd.service
	## centOS7以下，则：service ntpd stop
	[root@hadoop0-110 ~]# systemctl disable ntpd.service
	## centOS7以下，则：chkconfig ntpd off
	[root@hadoop0-110 ~]# systemctl status ntpd，查看ntp服务状态
	[root@hadoop0-110 ~]# pgrep ntpd，查看ntp服务进程id
	## 同步第一台服务器linux01的时间：
	[root@hadoop0-110 ~]# ntpdate hadoop102
	## 使用root用户制定计划任务,周期性同步时间：
	[root@hadoop0-110 ~]# crontab -e
	*/10 * * * * /usr/sbin/ntpdate hadoop102
	## 重启定时任务：
	[root@hadoop0-110 ~]# systemctl restart crond.service，
	centOS7以下使用：service crond restart
	## 其他台机器的配置同理
```

```shell
## 1. 配置oozie-site.xml文件
属性：oozie.processing.timezone
属性值：GMT+0800
解释：修改时区为东八区区时
注：该属性去oozie-default.xml中找到即可
## 2. 修改js框架中的关于时间设置的代码
[root@hadoop0-110 ~]# vim /opt/module/cdh/oozie-4.0.0-cdh5.3.6/oozie-server/webapps/oozie/oozie-console.js
修改如下：
function getTimeZone() {
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
    return Ext.state.Manager.get("TimezoneId","GMT+0800");
}
## 3.重启oozie服务，并重启浏览器（一定要注意清除缓存）
[atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ bin/oozied.sh stop
[atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ bin/oozied.sh start
## 4.拷贝官方模板配置定时任务\
$ cp -r examples/apps/cron/ oozie-apps/
## 5.修改模板job.properties和coordinator.xml以及workflow.xml
	## job.properties
	nameNode=hdfs://hadoop102:8020
    jobTracker=hadoop103:8032
    queueName=default
    examplesRoot=oozie-apps
    oozie.coord.application.path=${nameNode}/user/${user.name}/${examplesRoot}/cron
    #start：必须设置为未来时间，否则任务失败
    start=2017-07-29T17:00+0800
    end=2017-07-30T17:00+0800
    workflowAppUri=${nameNode}/user/${user.name}/${examplesRoot}/cron
    EXEC3=p3.sh
   	## coordinator.xml
	<coordinator-app name="cron-coord" frequency="${coord:minutes(5)}" start="${start}" end="${end}" timezone="GMT+0800" xmlns="uri:oozie:coordinator:0.2">
    <action>
        <workflow>
            <app-path>${workflowAppUri}</app-path>
            <configuration>
                <property>
                    <name>jobTracker</name>
                    <value>${jobTracker}</value>
                </property>
                <property>
                    <name>nameNode</name>
                    <value>${nameNode}</value>
                </property>
                <property>
                    <name>queueName</name>
                    <value>${queueName}</value>
                </property>
            </configuration>
        </workflow>
    </action>
    </coordinator-app>
    ## workflow.xml
    <workflow-app xmlns="uri:oozie:workflow:0.5" name="one-op-wf">
    <start to="p3-shell-node"/>
      <action name="p3-shell-node">
          <shell xmlns="uri:oozie:shell-action:0.2">
              <job-tracker>${jobTracker}</job-tracker>
              <name-node>${nameNode}</name-node>
              <configuration>
                  <property>
                      <name>mapred.job.queue.name</name>
                      <value>${queueName}</value>
                  </property>
              </configuration>
              <exec>${EXEC3}</exec>
              <file>/user/atguigu/oozie-apps/cron/${EXEC3}#${EXEC3}</file>
              <!-- <argument>my_output=Hello Oozie</argument>-->
              <capture-output/>
          </shell>
          <ok to="end"/>
          <error to="fail"/>
      </action>
    <kill name="fail">
        <message>Shell action failed, error message[${wf:errorMessage(wf:lastErrorNode())}]</message>
    </kill>
    <kill name="fail-output">
        <message>Incorrect output, expected [Hello Oozie] but was [${wf:actionData('shell-node')['my_output']}]</message>
    </kill>
    <end name="end"/>
    </workflow-app>
    ## 上传配置
    [atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ /opt/module/cdh/hadoop-2.5.0-cdh5.3.6/bin/hdfs dfs -put oozie-apps/cron/ /user/admin/oozie-apps
    ## 启动任务
    [atguigu@hadoop102 oozie-4.0.0-cdh5.3.6]$ bin/oozie job -oozie http://hadoop102:11000/oozie -config oozie-apps/cron/job.properties -run
    ## 注意：Oozie允许的最小执行任务的频率是5分钟
```

# 三：第5章 常见问题总结

>1）Mysql权限配置
>
>授权所有主机可以使用root用户操作所有数据库和数据表
>
>mysql> grant all on *.* to root@'%' identified by '000000';
>
>mysql> flush privileges;
>
>mysql> exit;
>
>2）workflow.xml配置的时候不要忽略file属性
>
>3）jps查看进程时，注意有没有bootstrap
>
>4）关闭oozie
>
>如果bin/oozied.sh stop无法关闭，则可以使用kill -9 [pid]，之后oozie-server/temp/xxx.pid文件一定要删除。
>
>5）Oozie重新打包时，一定要注意先关闭进程，删除对应文件夹下面的pid文件。（可以参考第4条目）
>
>6）配置文件一定要生效
>
>起始标签和结束标签无对应则不生效，配置文件的属性写错了，那么则执行默认的属性。
>
>7）libext下边的jar存放于某个文件夹中，导致share/lib创建不成功。
>
>8）调度任务时，找不到指定的脚本，可能是oozie-site.xml里面的Hadoop配置文件没有关联上。
>
>9）修改Hadoop配置文件，需要重启集群。一定要记得scp到其他节点。
>
>10）JobHistoryServer必须开启，集群要重启的。
>
>11）Mysql配置如果没有生效的话，默认使用derby数据库。
>
>12）在本地修改完成的job配置，必须重新上传到HDFS。
>
>13）将HDFS中上传的oozie配置文件下载下来查看是否有错误。
>
>14）Linux用户名和Hadoop的用户名不一致。







