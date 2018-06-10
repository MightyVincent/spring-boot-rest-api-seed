package com.company.project.core

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView


/**
 * global beans
 * @author VincentLee
 */
@Component
class GB : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        Companion.applicationContext = applicationContext
    }

    @Autowired
    fun setGson(gson: Gson) {
        Companion.gson = gson
    }

    @Autowired
    fun setAppProperties(appProperties: AppProperties) {
        Companion.appProperties = appProperties
    }

    companion object {
        lateinit var gson: Gson
        lateinit var applicationContext: ApplicationContext
        lateinit var appProperties: AppProperties
        val emptyModelAndView: ModelAndView = ModelAndView()
    }
}