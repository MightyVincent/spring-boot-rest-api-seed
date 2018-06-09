package com.company.project.configurer

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import com.company.project.model.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.hateoas.Resource
import org.springframework.hateoas.ResourceProcessor
import org.springframework.http.converter.HttpMessageConverter
import java.nio.charset.Charset

/**
 * @author VincentLee
 */
@Configuration
class SpringDataRestConfig : RepositoryRestConfigurerAdapter() {

    override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration) {
        config.corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true)
//        config.defaultMediaType = MediaType.APPLICATION_JSON_UTF8
    }


    override fun configureHttpMessageConverters(messageConverters: MutableList<HttpMessageConverter<*>>) {
        val converter = FastJsonHttpMessageConverter()
        val config = FastJsonConfig()
        config.setSerializerFeatures(/*SerializerFeature.WriteMapNullValue, //保留空的字段*/
                SerializerFeature.WriteNullStringAsEmpty, //String null -> ""
                SerializerFeature.WriteNullNumberAsZero)//Number null -> 0
        converter.fastJsonConfig = config
        converter.defaultCharset = Charset.forName("UTF-8")
        messageConverters.add(converter)
    }

    @Bean
    fun personProcessor(): ResourceProcessor<Resource<User>> {

        return ResourceProcessor<Resource<User>>() {

            @Override
            fun Resource < User > process (Resource<User> resource) {

                resource.add(new Link ("http://localhost:8080/people", "added-link"));
                return resource;
            }
        };
    }

}
