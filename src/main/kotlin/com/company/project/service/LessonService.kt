package com.company.project.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class LessonService {
    @Autowired
    private lateinit var personService: PersonService

    @PostConstruct
    public fun test() {
        println("------------------------")
        println(personService)
        println(a())
        println(b())
        println("------------------------")
    }

    public fun a(): String {
        return "a" + personService.b()
    }

    public fun b(): String {
        return "b" + personService.a()
    }
}
