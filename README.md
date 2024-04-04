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
Standard | Recommended        | Mandatory | Reference
---|--------------------| --- | ---
HTTP Client | Open Feign         | &check;| [Link1](https://cloud.spring.io/spring-cloud-openfeign) [Link2](https://www.baeldung.com/spring-cloud-openfeign)
Database Repository | Jpa Repository     | &check;| [Link](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories)
Log Interceptor| Zalando            | &check;|[Link](https://github.com/zalando/logbook)
Validator| Spring validation  | &check;|[Link](https://www.baeldung.com/spring-boot-bean-validation)
Utility | Lombok , Mapstruct | &check;| [Lombok](https://projectlombok.org/) [MapStruct](https://stackabuse.com/guide-to-mapstruct-in-java-advanced-mapping-library/)
Documentation| Spring doc         | &cross;|[Springdoc](https://springdoc.org)
Auditing Database| Envers             | &cross;| [Link](https://hibernate.org/orm/envers/)

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

## Developers

- Developer 1 - [developer1@akarinti.tech](mailto:developer1@akarinti.tech)
- Developer 2 - [developer2@akarinti.tech](mailto:developer2@akarinti.tech)

## Use BaseSpesification


1. in pom.xml add dependency

	 <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>33.0.0-jre</version>
        </dependency>

2. create child class of BaseSpecification and must be extends from super class
	example: UserSpecification extends BaseSpecification

3. create fileter searching like below
	 @Override
  	 protected List<String> getDefaultSearchField() {
    		return List.of("username","email","phoneNumber","firstName","lastName");
  	}

4. complete code

	

@Component
public class UserSpecification extends BaseSpecification {

  @Override
  protected List<String> getDefaultSearchField() {
    return List.of("username","email","phoneNumber","firstName","lastName");
  }

  public Specification<User> predicate(UserParam param) {
    return (root, query, builder) -> {
      List<Predicate> predicates = new ArrayList<>();
      List<Predicate> customSearchPredicate = new ArrayList<>();
      Join<Role, User> userJoin = root.join(StaticConstant.ROLE_ID); 
      customSearchPredicate.add(builder.like(builder.lower(userJoin.get("name").as(String.class)),
				("%" + param.getSearch() + "%").toLowerCase()));

      filterSearch(root, predicates, builder, param.getSearch(), customSearchPredicate);

      return builder.and(predicates.toArray(new Predicate[] {}));
    };
  }
 
}



