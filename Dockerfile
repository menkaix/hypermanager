FROM gradle:jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src
RUN gradle build --no-daemon 
RUN ls /home/gradle/src/build/libs/

FROM openjdk:17

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar /app/spring-boot-application.jar

COPY  cloud-run-invoker.json .

EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/app/spring-boot-application.jar"]
