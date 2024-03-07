# 拉取 jdk 作为基础镜像
#FROM openjdk:17.0.2-oracle
FROM openjdk:21-jdk-oracle
# 作者
MAINTAINER 渔民小镇 <262610965@qq.com>
# 添加jar到镜像并命名为 one.jar
ADD ./target/simple-one-1.0-SNAPSHOT-jar-with-dependencies.jar simple-one-game.jar
# 镜像启动后暴露的端口
EXPOSE 10100
# jar运行命令，参数使用逗号隔开
ENTRYPOINT ["java","-jar","simple-one-game.jar"]
