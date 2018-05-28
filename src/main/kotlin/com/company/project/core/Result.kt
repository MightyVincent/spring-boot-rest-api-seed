package com.company.project.core

import com.alibaba.fastjson.JSON

/**
 * 统一API响应结果封装
 */
class Result {
    var code: Int = 0
        private set
    var message: String? = null
    var data: Any? = null

    fun setCode(resultCode: ResultCode): Result {
        this.code = resultCode.code()
        return this
    }

    fun setMessage(message: String): Result {
        this.message = message
        return this
    }


    fun setData(data: Int?): Result {
        this.data = data
        return this
    }

    override fun toString(): String {
        return JSON.toJSONString(this)
    }
}
