#build maven
FROM maven:3.8.5-openjdk-17-slim AS build_maven
WORKDIR /app

# Copy the pom.xml and any other files required for dependency resolution first
COPY pom.xml ./
# Copy other files that are required for dependency resolution
COPY src/main/resources /app/src/main/resources

# Download dependencies (cache step)
RUN mvn dependency:go-offline


COPY . .

RUN mvn clean compile
RUN mvn install -DskipTests

#deploy to java17
FROM openjdk:17-alpine
VOLUME /tmp
COPY --from=build_maven /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
