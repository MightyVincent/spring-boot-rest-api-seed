package com.company.project.common.constant

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "project")
class ProjectProperties {
    lateinit var adminUsername: String
    lateinit var adminPassword: String
    lateinit var jwtSecret: String
}
