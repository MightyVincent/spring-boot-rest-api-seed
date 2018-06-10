package com.company.project.configurer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author VincentLee
 */
@Configuration
@EnableWebMvc
class MvcConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }

    @Bean
    fun gson(): Gson {
        val b = GsonBuilder()
        b.registerTypeAdapterFactory(DateTypeAdapter.FACTORY)
        return b.create()
    }

    @Bean
    fun gsonHttpMessageConverter(): GsonHttpMessageConverter {
        val gsonHttpMessageConverter = GsonHttpMessageConverter()
        gsonHttpMessageConverter.gson = gson()
        return gsonHttpMessageConverter
    }

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.removeIf { it is MappingJackson2HttpMessageConverter }
        converters.add(gsonHttpMessageConverter())
    }

}