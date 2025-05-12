# 第一阶段：使用临时镜像将文件解压
FROM openjdk:8-jre as builder

WORKDIR application

ADD ./target/*.jar ./app.jar

RUN java -Djarmode=layertools -jar app.jar extract

# 第二阶段：分层打包
FROM openjdk:8-jre

MAINTAINER chenboprogrammer@gmail.com

WORKDIR application

COPY --from=builder application/dependencies/ ./

COPY --from=builder application/spring-boot-loader/ ./

COPY --from=builder application/snapshot-dependencies/ ./

COPY --from=builder application/application/ ./

# 声明容器暴露的端口，便于使用者了解容器开启的端口
EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
