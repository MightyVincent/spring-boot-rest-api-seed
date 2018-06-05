package com.company.project.common.constant

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author VincentLee
 */
@Configuration
@ConfigurationProperties(prefix = "app")
class AppProperties {
}
