# spring-boot-rest-api-seed

## 简介 Brief

一个基于Spring Boot及相关组件定制的种子项目，用于快速构建中小型API、RESTful API项目，开箱即用，减少重复工作，节约时间?:D。

## 核心 Core & 特性 Features

1. Gradle
1. Kotlin
1. Spring Data JPA
1. [Spring Data REST](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)
    1. 关于@Projection接口：可以用来过滤或格式化属性，必须和@Entity类同包或子包
1. Spring Security

```
POST http://localhost:8080/login?username=admin&password=123

GET http://localhost:8080/api/users
X-Requested-With: XMLHttpRequest
X-Auth-Token: 2dc26be2-0eb1-49b8-9fcc-6e137160b6b3
```

 ## 致谢 Reference

1. [lihengming/spring-boot-api-project-seed](https://github.com/lihengming/spring-boot-api-project-seed)
