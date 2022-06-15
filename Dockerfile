FROM adoptopenjdk/openjdk11:alpine

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ARG JAR_FILE=target/*jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Xms128M","-Xmx128M","-XX:PermSize=64m","-XX:MaxPermSize=128m","-jar","/app.jar"]
