package com.company.project.core


import java.lang.reflect.ParameterizedType

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
abstract class AbstractService<T> : Service<T> {

//    @Autowired
//    protected lateinit var mapper: Mapper<T>

    private val modelClass: Class<T>    // 当前泛型真实类型的Class

    init {
        val pt = this.javaClass.genericSuperclass as ParameterizedType
        @Suppress("UNCHECKED_CAST")
        modelClass = pt.actualTypeArguments[0] as Class<T>
    }

    override fun modelClass(): Class<T> {
        return modelClass
    }

    /*override fun mapper(): Mapper<T> {
        return mapper
    }*/
}
