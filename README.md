# spring-boot-rest-api-seed

## 简介 Brief

一个基于Spring Boot及常用组件定制的种子项目，用于快速构建中小型RESTful API项目，开箱即用，减少重复编码，节约时间。

An customized seed project based on Spring Boot and lots of the regular used frameworks, out-of-the-box and intended to reduce repetitions coding and save time.

## 核心 Core & 特性 Features

1. Gradle
    1. [Gradle Build Language Reference](https://docs.gradle.org/current/dsl/)
1. Kotlin
    1. [Basic Syntax](http://kotlinlang.org/docs/reference/basic-syntax.html)
1. Spring Data JPA
    1. [Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/2.0.2.RELEASE/reference/html/)
1. Spring Session
    1. [Spring Session Reference](https://docs.spring.io/spring-session/docs/current/reference/html5/)
1. Spring Security
    1. [Spring Security Reference](https://docs.spring.io/spring-security/site/docs/5.0.5.RELEASE/reference/htmlsingle/)
    ```
    # 登录以取得名为'X-Auth-Token'的响应头
    # login and get the response header named by 'X-Auth-Token'
    POST http://localhost:8080/login?username=xxx&password=xxx

    # 以'X-Auth-Token'作为请求头请求需要授权的资源
    # request authenticated resources with the 'X-Auth-Token' header
    GET http://localhost:8080/admin/users
    X-Auth-Token: xxx
    ```
1. Gson
    1. [Gson User Guide](https://github.com/google/gson/blob/master/UserGuide.md)
