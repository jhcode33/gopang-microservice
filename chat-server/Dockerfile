##stage 1
##Start with a base image containing Java runtime
#FROM openjdk:17 as build
## Add Maintainer Info
#LABEL maintainer="Illary Huaylupo <illaryhs@gmail.com>"
## The application's jar file
#ARG JAR_FILE
## Add the application's jar to the container
#COPY ${JAR_FILE} app.jar
##unpackage jar file
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)
##stage 2
##Same Java runtime
#FROM openjdk:17
##Add volume pointing to /tmp
#VOLUME /tmp
##Copy unpackaged application to new container
#ARG DEPENDENCY=/target/dependency
#COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
##execute the application
##D:\workspace\service\chat-server\src\main\java\chat\example\demo\DemoApplication.java
##chat.example.demo.DemoApplication
#ENTRYPOINT ["java","-cp","app:app/lib/*","chat.example.demo.DemoApplication"]

FROM openjdk:17-jdk

#RUN apt-get update && apt-get -y install sudo

ARG JAR_FILE="build/libs/demo-0.0.1-SNAPSHOT.jar"

COPY ${JAR_FILE} app.jar

ENV    PROFILE dev

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar","/app.jar"]