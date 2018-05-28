package com.company.project.core

import org.jetbrains.annotations.NotNull

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
interface Service<T> {


    @NotNull
    fun modelClass(): Class<T>

    /*@Throws(TooManyResultsException::class)
    fun findByField(fieldName: String, value: Any): T {
        try {
            val model: T = modelClass().newInstance()
            val field = modelClass().getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(model, value)
            return mapper().selectOne(model)
        } catch (e: ReflectiveOperationException) {
            throw ServiceException(e.message, e)
        }

    }*/
}
