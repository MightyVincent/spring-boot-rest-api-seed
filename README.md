# spring-boot-rest-api-seed

## 简介 Brief

一个基于Spring Boot的种子项目，用于快速构建中小型API、RESTful API项目。

起源于某种子项目[lihengming/spring-boot-api-project-seed](https://github.com/lihengming/spring-boot-api-project-seed)，使用之后发现诸多不如我意的地方，遂想定制一个。

定制期间浏览了一遍`Spring Initializr`上的所有组件，又捡起了好久之前看到的当时还不完善的`Spring Data REST`，读过一遍API后，发现该组件从各方面完完全全能够满足一个REST种子项目的所有需求，其强大简直令人咋舌。

于是基于此组件以及个人常用的东西定制了本种子项目，以减少重复工作，开箱即用，节约时间好撩妹?:D

## 核心 Core & 特性 Features

1. Gradle
1. Kotlin
1. Spring Data JPA
1. [Spring Data REST](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)
    1. 关于@Projection接口：可以用来过滤或格式化属性，必须和@Entity类同包或子包
1. Spring Security

<!-- ## 致谢 Reference -->

