package com.company.project.core

import com.google.gson.Gson
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass


/**
 * global params & beans
 * @author VincentLee
 */
object G {
    lateinit var gson: Gson
    lateinit var applicationContext: ApplicationContext
    lateinit var appProperties: AppProperties
    val emptyModelAndView: ModelAndView = ModelAndView()

    /**
     * logger缓存
     */
    internal val loggerCache = ConcurrentHashMap<KClass<*>, Logger>()

    @Component
    internal class GInit : ApplicationContextAware {
        override fun setApplicationContext(applicationContext: ApplicationContext) {
            G.applicationContext = applicationContext
        }

        @Autowired
        fun setGson(gson: Gson) {
            G.gson = gson
        }

        @Autowired
        fun setAppProperties(appProperties: AppProperties) {
            G.appProperties = appProperties
        }
    }
}