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
