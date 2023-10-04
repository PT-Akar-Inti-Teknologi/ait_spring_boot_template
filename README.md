# `AIT SPRING BOOT GUIDELINE EXAMPLE`
## `By PT Akarinti Teknologi`

link guideline [Link](https://docs.google.com/presentation/d/1i8YEQ6zQlcZDB7472bNmymiwLluMaMCMVzoaj_Awk6U/edit?usp=sharing)

## `Specification:`

Standard | Recommended 
---|--- 
Java | Recommended using java 17
Spring Boot | 2.x.x (Stable Version)

### `Standard Dependency:`
Standard | Recommended | Mandatory | Reference
---|--- | --- | ---
HTTP Client | Open Feign | &check;| [Link1](https://cloud.spring.io/spring-cloud-openfeign) [Link2](https://www.baeldung.com/spring-cloud-openfeign)
Database Repository | Jpa Repository | &check;| [Link](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories)
Log Interceptor|Zalando | &check;|[Link](https://github.com/zalando/logbook)
Validator|Spring validation| &check;|[Link](https://www.baeldung.com/spring-boot-bean-validation)
Utility |Lombok , Mapstruct | &check;| [Lombok](https://projectlombok.org/) [MapStruct](https://stackabuse.com/guide-to-mapstruct-in-java-advanced-mapping-library/)
Documentation|Swagger / Postman Documentation | &cross;|[Swagger](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui/1.7.0) [Postman Docs](https://learning.postman.com/docs/publishing-your-api/documenting-your-api/)
Auditing Database|Envers | &cross;| [Link](https://hibernate.org/orm/envers/)

## `Package Structure`

> **Base Package** : org.ait.project.{project name}

``
{project name} dapat di ganti sesuai dengan project yang sedang dikerjakan 
``

## `Structure:`

```
src
--|config
-----|security
-----|exception
-----|package-config-lainnya
-----|ApplicationProperties.java
-----|MessageConfig.java
--|module
-----|module-A
--------| interfaces
------------| rest
----------------| Case1Controller.java
----------------| Case2Controller.java
------------| messaging
----------------| KafkaConsumerModuleA1.java
----------------| KafkaConsumerModuleA2.java
------------| scheduler
----------------| SchedulerModuleA1.java
----------------| SchedulerModuleA2.java
--------| dto
------------| request
----------------| UserReq.java
------------| response
----------------| UserRes.java
------------| param
----------------| UserParam.java
--------| exception
------------| UserNotFoundException.java
--------| model
------------| entity
------------| repository
------------| specification
---------| service
------------| core ( Process Bussines )
----------------|impl
-------------------| UserServiceImpl.java             
----------------| UserService.java
------------| adapter
----------------| command ( Create, update, delete )
--------------------|impl
-----------------------| UserCommandAdapterImpl.java
--------------------| UserCommandAdapter.java
----------------| query ( Read )
--------------------|impl
-----------------------| UserQueryAdapterImpl.java
--------------------|UserQueryAdapter.java
---------| transform
------------| UserTransform.java
-----|module-B
--|shared
-----|constants
---------|enum
---------|static
-----|feignclient
---------|thridparty
-------------|facade
----------------|fallback
-------------------|ThirdPartyClientFallback.java
----------------|ThirdPartyClient.java
-------------|request
-----------------|request.java
-------------|response
-----------------|response.java
-------------|service
-----------------|impl
--------------------|ThirdpartyServiceImpl.java
-----------------|ThirdpartyService.java
-----|template
-----|utils
---------|transform
---------|response
-------------|ResponseHelper.java
-------------|ResponseMessageHelper.java
--|main.java
```
