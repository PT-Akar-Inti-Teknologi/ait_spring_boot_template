#build maven
FROM maven:3.8.5-openjdk-17-slim AS build_maven
WORKDIR /app

COPY . .

RUN  --mount=type=cache,target=/root/.m2  mvn clean compile
RUN  --mount=type=cache,target=/root/.m2  mvn install -DskipTests

#deploy to java17
FROM openjdk:17-alpine
VOLUME /tmp
COPY --from=build_maven /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
