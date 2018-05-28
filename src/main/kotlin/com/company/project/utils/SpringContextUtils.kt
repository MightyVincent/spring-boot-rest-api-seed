package com.company.project.utils

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * Created by Administrator on 2018/5/3 0003.
 */
@Component
class SpringContextUtils : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContextUtils.applicationContext = applicationContext
    }

    companion object {

        lateinit var applicationContext: ApplicationContext

        /**
         * 通过name获取 Bean.
         *
         * @param name
         * @return
         */
        fun getBean(name: String): Any {
            return applicationContext.getBean(name)
        }

        /**
         * 通过class获取Bean.
         *
         * @param clazz
         * @param <T>
         * @return
        </T> */
        fun <T> getBean(clazz: Class<T>): T {
            return applicationContext.getBean(clazz)
        }

        /**
         * 通过name,以及Clazz返回指定的Bean
         *
         * @param name
         * @param clazz
         * @param <T>
         * @return
        </T> */
        fun <T> getBean(name: String, clazz: Class<T>): T {
            return applicationContext.getBean(name, clazz)
        }
    }

}
