package com.company.project.utils

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component

import javax.annotation.Resource

/**
 * @author VincentLee
 */
@Component
object CacheUtil {

    private lateinit var cacheManager: CacheManager

    @Resource
    fun setCacheManager(cacheManager: CacheManager) {
        this.cacheManager = cacheManager
    }

    fun getCache(name: String): Cache? {
        return cacheManager.getCache(name)
    }

    val cacheNames: Collection<String>
        get() = cacheManager.cacheNames

}
