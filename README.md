# `AIT SPRING BOOT GUIDELINE EXAMPLE`
## `By PT Akarinti Teknologi`

link guideline [Link](https://docs.google.com/presentation/d/1i8YEQ6zQlcZDB7472bNmymiwLluMaMCMVzoaj_Awk6U/edit?usp=sharing)

## `Specification:`

Standard | Recommended 
---|--- 
Java | Recommended using java 8
Spring Boot | 2.x.x (Stable Version)

### `Standard Dependency:`
Standard | Recommended | Mandatory | Reference
---|--- | --- | ---
HTTP Client | Open Feign | &check;| [Link1](https://cloud.spring.io/spring-cloud-openfeign) [Link2](https://www.baeldung.com/spring-cloud-openfeign)
Database Repository | Jpa Repository | &check;| [Link](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories)
Log Interceptor|Zalando | &check;|[Link](https://github.com/zalando/logbook)
Validator|Spring validation| &check;|[Link](https://www.baeldung.com/spring-boot-bean-validation)
Utility |Lombok , Mapstruct | &check;| [Lombok](https://projectlombok.org/) [MapStruct](https://stackabuse.com/guide-to-mapstruct-in-java-advanced-mapping-library/)
Documentation|Swagger / Postman Documentation | &cross;|[Swagger](https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api) [Postman Docs](https://learning.postman.com/docs/publishing-your-api/documenting-your-api/)
Auditing Database|Envers | &cross;| [Link](https://hibernate.org/orm/envers/)

## `Package Structure`

> **Base Package** : org.ait.project.{project name}

``
{project name} dapat di ganti sesuai dengan project yang sedang dikerjakan 
``

## `Structure:`

```
+ org.ait.project.project
+----| config 
+------| {config package & class}
+----| modules
+--------| Module A
+----------| controller
+------------| ModuleA1Controller.java
+------------| ModuleA2Controller.java
+----------| dto
+------------| data
+------------| request
+------------| response
+------------| transform
+--------------| {mapstruct class}
+----------| exception
+----------| model
+------------| entity
+--------------| id
+----------------| {embeded id class}
+--------------| ModuleAEntity1.java
+--------------| ModuleAEntity2.java
+------------| repository
+--------------| ModuleAEntity1Repository.java
+--------------| ModuleAEntity2Repository.java
+------------| transform
+--------------| {mapstruct class}
+----------| service
+------------| internal
+--------------| ModuleA1Service.java
+--------------| ModuleA2Service.java
+------------| delegate
+--------------| impl
+----------------| ModuleAEntity1DelegateImpl.java
+----------------| ModuleAEntity2DelegateImpl.java
+--------------| ModuleAEntity1Delegate.java {interface}
+--------------| ModuleAEntity2Delegate.java {interface}
+--------| Module B
+----------| dto
+------------| {object data}
+----------| exception
+----------| model
+------------| entity
+--------------| id
+----------------| {embeded id class}
+--------------| ModuleBEntity1.java
+--------------| ModuleBEntity2.java
+------------| repository
+--------------| ModuleBEntity1Repository.java
+--------------| ModuleBEntity2Repository.java
+------------| transform
+--------------| {mapstruct class}
+----------| service
+------------| delegate
+--------------| impl
+----------------| ModuleBEntity1DelegateImpl.java
+----------------| ModuleBEntity2DelegateImpl.java
+--------------| ModuleBEntity1Delegate.java {interface}
+--------------| ModuleBEntity2Delegate.java {interface}
+--------| {Module On Project}
+----| shared
+------| dto {shared dto}
+------| openfeign 
+------| constants
+--------| enums
+------| utils
+----| Application.java
```

### `Resume`

**Package Config**: merupakan package yang berisi component-component spring, seperti openfeign, spring security, dan hal-hal lain yang merupakan bawaan spring.

**Package Shared**: merupakan package yang berisi class-class yang digunakan di seluruh project.

**Package openfeign**: ada 2 package openfeign 1 ada pada config dan ada pada shared, untuk package yang ada pada config diperuntukan file java untuk configurasi yang digunakan oleh openfeign interface. untuk package yang ada di shared, merupakan package yang berisikan file interface dan body request serta response yang akan dipakai oleh doman.

**Package Constant**: berisikan package enum, dan file-file java yang merupakan constant value

**Package Enums**: merupakan package yang berisikan file-file enum.java yang digunakan di seluruh project.

_*note: untuk enum yang digunakan oleh Entity di letakkan satu layer dengan modelnya._

**Package dto**: package DTO yang ada pada package shared merupakan DTO yang digunakan oleh seluruh project.

**Package Utils**: merupakan package yang berisikan file java yang memiliki function-function yang digunakan di seluruh project.

**Package Modules**: merupakan package yang berisikan modul-modul atau inti bisnis dari project.

**Package Module Berisikan**:

**Controller**: merupakan package yang berisikan file controller yang mengatur input API serta path uri sebuah API.

**DTO**: merupakan package yang berisikan file class data yang digunakan sebagai input API, output, ataupun data.

**request**: berisikan file class data input yang akan di isi oleh controller.

**response**: berisikan file class data output yang akan dikembalikan saat respone API

**data**: berisikan file class data yang digunakan sebagai data pivot / data yang biasanya digunakan sebagai parameter

**Service**: merupakan package yang berisikan otak bisnis dalam sebuah module yang di bagi menjadi internal dan delegate.

**internal**: merupakan package service yang berisikan file java yang memproses data yang datang dari file controller.

**delegate**: merupakan package service yang berisikan file java yang memproses data pada model yang digunakan oleh service internal modul ataupun service internal diluar module

**Model**: merupakan package yang berisikan entity dan repository yang merepresentasikan database.

_*note: jika modul menggunakan lebih dari 1 database, diharuskan membuat package yang memisahkan modelnya. jika hanya ada 1 langsung tanpa adanya package tambahan. (contoh bisa dilihat dari struktur di atas, antara example module a dan module b)_

**Entity**: merupakan package yang berisikan entity yang merepresentasikan data dalam database.

**Repository**: merupakan package yang berisikan file interface repository yang digunakan untuk mengolah data dalam database.

(Optional) **id**: merupakan package yang berisikan file composite key dipakai didalam entity.

**Transform**: merupakan package yang berisikan file-file mapper interface MapStruct.

