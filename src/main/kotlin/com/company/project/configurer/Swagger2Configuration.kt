/*
package com.company.project.configurer

import org.springframework.context.annotation.Bean
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

//@Configuration
//@EnableSwagger2
class Swagger2Configuration {

    @Bean
    fun buildDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.company.project.web"))
                .paths(PathSelectors.any())
                .build()
    }

    private fun buildApiInf(): ApiInfo {
        return ApiInfoBuilder()
                .title("")
                .description("")
                .termsOfServiceUrl("")
                .contact(Contact("", "", ""))
                .build()

    }

}
*/
