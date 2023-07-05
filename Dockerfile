FROM gradle:jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/crowdfunding-platform-api.jar

ENTRYPOINT ["java", "-jar","/app/crowdfunding-platform-api.jar"]