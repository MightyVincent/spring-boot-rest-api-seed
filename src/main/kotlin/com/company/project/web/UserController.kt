package com.company.project.web

import com.company.project.dao.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


/**
 * @author VincentLee
 */
@RepositoryRestController
class UserController {
    @Autowired
    private lateinit var repository: UserRepository


    @GetMapping(value = ["/scanners/search/listProducers"])
    @ResponseBody
    fun getProducers(): ResponseEntity<*> {
        val producers = repository.listProducers()

        //
        // do some intermediate processing, logging, etc. with the producers
        //

        val resources = Resources<String>(producers)

        resources.add(linkTo(methodOn(ScannerController::class.java).getProducers()).withSelfRel())

        // add other links as needed

        return ResponseEntity.ok<Any>(resources)
    }

}
