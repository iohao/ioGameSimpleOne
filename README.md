## 介绍

**快速从零编写服务器完整示例-文档**

https://www.yuque.com/iohao/game/zm6qg2



**环境安装**

https://www.yuque.com/iohao/game/pe7gig



**运行步骤**

1. 服务器启动类 DemoApplication.java
2. 模拟客户端启动类 DemoClient.java



**打 jar 包运行**

> mvn clean package



## docker 部署

准备工作，确保机器上有 docker 相关环境。以下是在终端执行的，首次使用 docker 部署、运行需要的时间会长一些，因为会下载相关的镜像。



**1、 打 jar 包，在示例目录的根目录执行如下命令**

> mvnd package

执行完打 jar 包的命令后， target 目录下会有 simple-one-1.0-SNAPSHOT-jar-with-dependencies.jar 的 jar 文件。



**2、在示例目录的根目录执行如下命令** 

不要遗漏命令中的点 “.”;

> docker build -t simple-one-iogame .



**3、查看当前镜像**

> docker images simple-one-iogame

注意，第3步骤不是必须的。执行完这条命令后可以看见镜像是否存在。



**4、启动刚打包好的镜像**

> docker run --name simple-one-iogame -p 10100:10100 simple-one-iogame

---

[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://iohao.github.io/ioGameSimpleOne/javadoc/)