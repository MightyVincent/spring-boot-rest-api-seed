package com.company.project.core

import org.springframework.http.HttpStatus
import org.springframework.lang.NonNull
import org.springframework.lang.Nullable

/**
 * ResponseResult
 * @author VincentLee
 */
class RR<T>(
        var status: Int,
        var message: String,
        var content: T? = null
) {
    companion object {
        fun ok(): RR<Void> {
            return ResultBuilder(HttpStatus.OK).build()
        }

        fun <T> ok(content: T?): RR<T> {
            return ResultBuilder(HttpStatus.OK).content(content)
        }

        fun status(@NonNull status: HttpStatus): ResultBuilder {
            return ResultBuilder(status)
        }

        // static builder
        class ResultBuilder(
                var status: HttpStatus
        ) {

            fun build(): RR<Void> {
                return RR(this.status.value(), this.status.reasonPhrase)
            }

            fun message(@Nullable message: String?): RR<Void> {
                return RR(this.status.value(), message ?: this.status.reasonPhrase)
            }

            fun <T> content(@Nullable content: T?): RR<T> {
                return RR(this.status.value(), this.status.reasonPhrase, content)
            }

        }
    }
}