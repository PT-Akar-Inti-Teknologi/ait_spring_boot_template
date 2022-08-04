FROM maven:alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never -DskipTests
ADD . $HOME
RUN mvn package -DskipTests

FROM openjdk:8-jdk
COPY --from=build /usr/app/target/*.jar /app/example.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/example.jar"]