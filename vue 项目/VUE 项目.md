VUE 项目

# 一：element-ui

```shell
官网： http://element-cn.eleme.io/#/zh-CN

<!-- import CSS -->
<link rel="stylesheet" href="element-ui/lib/theme-chalk/index.css">

<!-- import Vue before Element -->
<script src="vue.min.js"></script>
<!-- import JavaScript -->
<script src="element-ui/lib/index.js"></script>
```

# 二：node

```shell
# 中文官网： 
http://nodejs.cn/  LTS：长期支持版本、Current：最新版

# 查看node 版本： 
node -v
# 查看当前npm版本
npm -v
```

## 1. 项目初始化

```shell
# node 项目初始化
#建立一个空文件夹，在命令提示符进入该文件夹  执行命令初始化
npm init
#按照提示输入相关信息，如果是用默认值则直接回车即可。
#name: 项目名称
#version: 项目版本号
#description: 项目描述
#keywords: {Array}关键词，便于用户搜索到我们的项目
#最后会生成package.json文件，这个是包的配置文件，相当于maven的pom.xml
#我们之后也可以根据需要进行修改。

#如果想直接生成 package.json 文件，那么可以使用命令
npm init -y
```

## 2. 修改 npm 镜像

```shell
#经过下面的配置，以后所有的 npm install 都会经过淘宝的镜像地址下载
npm config set registry https://registry.npm.taobao.org 

#查看npm配置信息
npm config list
```

## 3. 常见命令

```shell
#使用 npm install 安装依赖包的最新版，
#模块安装的位置：项目目录\node_modules
#安装会自动在项目目录下添加 package-lock.json文件，这个文件帮助锁定安装包的版本
#同时package.json 文件中，依赖包会被添加到dependencies节点下，类似maven中的 <dependencies>
npm install jquery

#npm管理的项目在备份和传输的时候一般不携带node_modules文件夹
npm install #根据package.json中的配置下载依赖，初始化项目

#如果安装时想指定特定的版本
npm install jquery@2.1.x

#devDependencies节点：开发时的依赖包，项目打包到生产环境的时候不包含的依赖
#使用 -D参数将依赖添加到devDependencies节点
npm install --save-dev eslint
#或
npm install -D eslint

#全局安装
#Node.js全局安装的npm包和工具的位置：用户目录\AppData\Roaming\npm\node_modules
#一些命令行工具常使用全局安装的方式
npm install -g webpack

#更新包（更新到最新版本）
npm update 包名
#全局更新
npm update -g 包名

#卸载包
npm uninstall 包名
#全局卸载
npm uninstall -g 包名
```

# 三：Babel

```shell
# 安装
npm install --global babel-cli

# 查看是否安装成功
babel --version

## 使用
# npm初始化
npm init -y
# 配置.babelrc。Babel的配置文件是.babelrc，存放在项目的根目录下，该文件用来设置转码规则和插件，基本格式如下。
{
    "presets": [],
    "plugins": []
}
# presets字段设定转码规则，将es2015规则加入 .babelrc：
{
    "presets": ["es2015"],
    "plugins": []
}
# 安装转码器
npm install --save-dev babel-preset-es2015

## 转码
# 转码结果写入一个文件
mkdir dist1
# --out-file 或 -o 参数指定输出文件
babel src/example.js --out-file dist1/compiled.js
# 或者
babel src/example.js -o dist1/compiled.js

# 整个目录转码
mkdir dist2
# --out-dir 或 -d 参数指定输出目录
babel src --out-dir dist2
# 或者
babel src -d dist2
```

# 四：Webpack

```shell
# 全局安装
npm install -g webpack webpack-cli
# 查看版本
webpack -v
```

## 1. JS打包

```shell
# webpack目录下创建配置文件webpack.config.js
# 以下配置的意思是：读取当前项目目录下src文件夹中的main.js（入口文件）内容，分析资源依赖，把相关的js文件打包，打包后的文件放入当前目录的dist文件夹下，打包后的js文件名为bundle.js
const path = require("path"); //Node.js内置模块
module.exports = {
    entry: './src/main.js', //配置入口文件
    output: {
        path: path.resolve(__dirname, './dist'), //输出路径，__dirname：当前文件所在路径
        filename: 'bundle.js' //输出文件
    }
}

# 命令行执行编译命令
webpack #有黄色警告
webpack --mode=development #没有警告
#执行后查看bundle.js 里面包含了上面两个js文件的内容并惊醒了代码压缩

## 也可以配置项目的npm运行命令，修改package.json文件
"scripts": {
    //...,
    "dev": "webpack --mode=development"
 }
 # 运行npm命令执行打包
 npm run dev
```

## 2. CSS打包

```shell
## Webpack 本身只能处理 JavaScript 模块，如果要处理其他类型的文件，就需要使用 loader 进行转换。
## Loader 可以理解为是模块和资源的转换器。
## 首先我们需要安装相关Loader插件，css-loader 是将 css 装载到 javascript；style-loader 是让 javascript 认识css
npm install --save-dev style-loader css-loader 

# 修改webpack.config.js
const path = require("path"); //Node.js内置模块
module.exports = {
    //...,
    output:{},
    module: {
        rules: [  
            {  
                test: /\.css$/,    //打包规则应用到以css结尾的文件上
                use: ['style-loader', 'css-loader']
            }  
        ]  
    }
}
```

# 五：vue-element-admin 后台模板

```shell
# vue-element-admin是基于element-ui 的一套后台管理系统集成方案。
# 功能：
https://panjiachen.github.io/vue-element-admin-site/zh/guide/
# GitHub地址：
https://github.com/PanJiaChen/vue-element-admin
# 项目在线预览：
https://panjiachen.gitee.io/vue-element-admin

# 解压压缩包
# 进入目录
cd vue-element-admin-master
# 安装依赖
npm install
# 启动。执行后，浏览器自动弹出并访问http://localhost:9527/
npm run dev
```

```shell
# vue-admin-template
# vueAdmin-template是基于vue-element-admin的一套后台管理系统基础模板（最少精简版），可作为模板进行二次开发。
# GitHub地址：
https://github.com/PanJiaChen/vue-admin-template
```

# 六;nginx 反向代理

```shell
http {
    server {
        listen       81; ## 修改默认的 80端口到81
        ......
    }，
    
    ......
	server { ## 可以多个server

		listen 8201;  ## 监听的端口
		server_name localhost; ## 主机

		location ~ /edu/ {     ## 匹配路径      
			 proxy_pass http://localhost:8101;  ## 转发服务器地址
		}
		
		location ~ /user/ {   
			 rewrite /(.+)$ /mock/5950a2419adc231f356a6636/vue-admin/$1  break; 
			 proxy_pass https://www.easy-mock.com;
		}
	}
}
```

```shell
# nginx 常见指令
## 开启
start nginx
## 关闭
nginx -s stop
## 重启
nginx -s reload
```

# 七：NUXT

## 1. 下载

```shell
## 1. 地址
https://github.com/nuxt-community/starter-template/archive/master.zip
## 2. 下载并解压到本地。更名为项目名:projetA
## 3. 修改package.json
    ## name、description、author（必须修改这里，否则项目无法安装）
     "name": "projetA",
     "version": "1.0.0",
     "description": "projetA网站",
     "author": "xiahan <xiahan@163.com>",
## 4. 修改nuxt.config.js
	## 修改title: '{{ name }}'、content: '{{escape description }}'这里的设置最后会显示在页面标题栏和meta数据中
	head: {
        title: 'projectA',
        meta: [
          { charset: 'utf-8' },
          { name: 'viewport', content: 'width=device-width, initial-scale=1' },
          { hid: 'keywords', name: 'keywords', content: 'ProjectA Content' },
          { hid: 'description', name: 'description', content: 'ProjectA Description' }
        ],
        link: [
          { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
        ]
    },
## 5. 安装依赖
npm install
```

# 八： 幻灯片插件

```shell
## 1.安装插件
npm install vue-awesome-swiper
## 2.配置插件
	# 在 plugins 文件夹下新建文件 nuxt-swiper-plugin.js，内容是
	import Vue from 'vue'
    import VueAwesomeSwiper from 'vue-awesome-swiper/dist/ssr'

    Vue.use(VueAwesomeSwiper)
    # 在 nuxt.config.js 文件中配置插件
    # 将 plugins 和 css节点 复制到 module.exports节点下
    module.exports = {
      // some nuxt config...
      plugins: [
        { src: '~/plugins/nuxt-swiper-plugin.js', ssr: false }
      ],
      css: [
        'swiper/dist/css/swiper.css'
      ]
    }
```































