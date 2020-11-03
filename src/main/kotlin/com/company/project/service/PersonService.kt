package com.company.project.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class PersonService {
    @Autowired
    private lateinit var lessonService: LessonService

    @PostConstruct
    public fun test(): Unit {
        println(lessonService)
    }

    public fun a(): String {
        return "a"
    }

    public fun b(): String {
        return "b"
    }
}
