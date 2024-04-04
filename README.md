[//]: # (DELETE WHEN CREATE PROJECT ==============)

# `AIT SPRING BOOT GUIDELINE EXAMPLE`
## `By PT Akarinti Teknologi`

link guideline [Link](https://pt-akar-inti-teknologi.github.io/techstack/java)

## `Specification:`

Standard | Recommended 
---|--- 
Java | Recommended using java 17
Spring Boot | 3.x.x (Stable Version)

### `Standard Dependency:`
Standard | Recommended                                                                  | Mandatory | Reference
---|------------------------------------------------------------------------------| --- | ---
HTTP Client | Open Feign                                                                   | &check;| [Link1](https://cloud.spring.io/spring-cloud-openfeign) [Link2](https://www.baeldung.com/spring-cloud-openfeign)
Database Repository | Jpa Repository                                                               | &check;| [Link](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories)
Log Interceptor| Zalando                                                                      | &check;|[Link](https://github.com/zalando/logbook)
Validator| Spring validation                                                            | &check;|[Link](https://www.baeldung.com/spring-boot-bean-validation)
Utility | Lombok , Mapstruct                                                           | &check;| [Lombok](https://projectlombok.org/) [MapStruct](https://stackabuse.com/guide-to-mapstruct-in-java-advanced-mapping-library/)
Documentation| Spring doc                                                                   | &cross;|[Springdoc](https://springdoc.org)
Auditing Database| Envers                                                                       | &cross;| [Link](https://hibernate.org/orm/envers/)
Test Containers| Postgres Container, Redis Container, MongoDB Container | &check; | [Postgres](https://mvnrepository.com/artifact/org.testcontainers/postgresql) [Redis](https://mvnrepository.com/artifact/com.redis/testcontainers-redis) [MongoDB](https://mvnrepository.com/artifact/org.testcontainers/mongodb)

[//]: # (DELETE WHEN CREATE PROJECT ==============)

# Project Name

Outline a brief description of the project. If there is a demo app please also put a link here.

[Sonar Badge - Quality Gates] [Sonar Badge - Bug] [Sonar Badge - Code Smells] [Sonar Badge - Coverage] [Sonar Badge - Reability] [Sonar Badge - Security] [Sonar Badge - Maintainability]

## Table of Contents
- [Project Name](#project-name)
    - [Table of Contents](#table-of-contents)
    - [General Information](#general-information)
    - [Technologies Used](#technologies-used)
    - [Setup](#setup)
    - [Developers](#developers)

## General Information
- Provide general information about the project here.
- What problem does it (intend to) solve?
- What is the purpose of this project?


## Technologies Used
- Tech 1 - version 1.0
- Tech 2 - version 2.0
- Tech 3 - version 3.0

## Setup

What are the project requirements/dependencies?

Proceed to describe how to install / setup one's local environment / get started with the project.

Optionally also describe how to setup a staging or production environment.

## Getting started with Testcontainers

Testcontainers is a testing library that provides easy and lightweight APIs for bootstrapping integration tests with real services wrapped in Docker containers.
Using Testcontainers, you can write tests talking to the same type of services you use in production without mocks or in-memory services.

In this guide, you will learn how to

- Implement a Test REST API using Testcontainers.

### Prerequisites:

- Java 17+
- Your favorite IDE (Intellij IDEA, Eclipse, NetBeans, VS Code)
- A Docker environment supported by Testcontainers https://www.testcontainers.org/supported_docker_environment/

### What we are going to achieve in this guide
We are going to create a Spring Boot project using Spring Data JPA together with Postgres and implement a REST API endpoint to return all the product details that are stored in the database.
Then we will test this API using the Testcontainers Postgres module.

### Add Testcontainers dependencies
Before writing Testcontainers based tests. Let’s add Testcontainers dependencies in <B>pom.xml</B> as follows:
Firstly, we’ll need to include the spring-boot-testcontainers dependency in our pom.xml:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-testcontainers</artifactId>
    <scope>test</scope>
</dependency>
```
After that, we can add other depedencies to write test container in our project:
```xml
<!-- https://mvnrepository.com/artifact/org.testcontainers/testcontainers -->
<dependency>
	<groupId>org.testcontainers</groupId>
	<artifactId>testcontainers</artifactId>
	<version>${latest.version}</version>
	<scope>test</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/org.testcontainers/junit-jupiter -->
<dependency>
	<groupId>org.testcontainers</groupId>
	<artifactId>junit-jupiter</artifactId>
	<version>1.19.3</version>
	<scope>test</scope>
</dependency>
```
For what the repository you used. Choose alternative dependency which following the project:

a. Testcontainers PostgreSQL :
```xml
<!-- https://mvnrepository.com/artifact/org.testcontainers/postgresql -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>${latest.version}</version>
    <scope>test</scope>
</dependency>
```
b. Testcontainers Redis
```xml
<!-- https://mvnrepository.com/artifact/com.redis/testcontainers-redis -->
<dependency>
	<groupId>com.redis</groupId>
	<artifactId>testcontainers-redis</artifactId>
	<version>${latest.version}</version>
</dependency>
```
c. Testcontainers MongoDB
```xml
<!-- https://mvnrepository.com/artifact/org.testcontainers/mongodb -->
<dependency>
	<groupId>org.testcontainers</groupId>
	<artifactId>mongodb</artifactId>
	<version>${latest.version}</version>
	<scope>test</scope>
</dependency>
```
d. Testcontainers MSSQL Server
```xml
<!-- https://mvnrepository.com/artifact/org.testcontainers/mssqlserver -->
<dependency>
	<groupId>org.testcontainers</groupId>
	<artifactId>mssqlserver</artifactId>
	<version>${latest.version}</version>
	<scope>test</scope>
</dependency>
```
In this guideline, we will use the postgres container for the sample. We can use the Testcontainers library to spin up a Postgres database instance as a Docker container and configure the application to talk to that database as follows:

```java
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    // PostgreSQL
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
	
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgres).join();
      
        // PostgreSQL
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        //TODO can use the other containers such as redis, mongodb etc
    }
}
```

Let us understand what is going on in this test.

- We have annotated the test class with the @SpringBootTest annotation together with the webEnvironment config, so that the test will run by starting the entire application on a random available port.
- We have created an instance of PostgreSQLContainer using the postgres:latest Docker image.
- The Postgres database runs on port 5432 inside the container and maps to a random available port on the host.
- We have registered the database connection properties dynamically obtained from the Postgres container using Spring Boot’s DynamicPropertyRegistry.

If you create a new integration test, use **extends** to take class BaseIntegrationTest like sample in bellow :
```java
class UserControllerTest extends BaseIntegrationTest {
    @Test
    void getUser() {
        //TODO create a scenario test
    }
}
```

## Developers

- Developer 1 - [developer1@akarinti.tech](mailto:developer1@akarinti.tech)
- Developer 2 - [developer2@akarinti.tech](mailto:developer2@akarinti.tech)

