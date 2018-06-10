package com.company.project.core

import org.springframework.util.StringUtils
import java.util.*

/**
 * @author VincentLee
 */
object Assert {

    fun notNull(x: Any?, message: String) {
        if (x == null) {
            throw ServiceException(message)
        }
    }

    fun notNull(vararg arrayOfX: String?, message: String) {
        if (Arrays.stream(arrayOfX).anyMatch({ it == null })) {
            throw ServiceException(message)
        }
    }

    fun isTrue(x: Boolean?, message: String) {
        if (x != true) {
            throw ServiceException(message)
        }
    }

    fun isTrue(vararg arrayOfX: Boolean?, message: String) {
        if (Arrays.stream(arrayOfX).anyMatch({ it != true })) {
            throw ServiceException(message)
        }
    }

    fun hasText(x: String?, message: String) {
        if (StringUtils.isEmpty(x)) {
            throw ServiceException(message)
        }
    }

    fun hasText(vararg arrayOfX: String?, message: String) {
        if (Arrays.stream(arrayOfX).anyMatch({ StringUtils.isEmpty(it) })) {
            throw ServiceException(message)
        }
    }

}