package com.company.project.configurer

import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfiguration : CachingConfigurerSupport()
