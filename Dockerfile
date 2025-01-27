FROM gradle:jdk21 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src
RUN gradle build --no-daemon 
RUN ls /home/gradle/src/build/libs/

FROM openjdk:21

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar /app/spring-boot-application.jar

EXPOSE 8080
CMD ["java", "-jar","/app/spring-boot-application.jar"]
