package com.company.project.core

import com.company.project.core.G.loggerCache
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author VincentLee
 */
fun HttpServletRequest.getStatus(): HttpStatus {
    val statusCode = this.getAttribute("javax.servlet.error.status_code") as Int?
            ?: return HttpStatus.INTERNAL_SERVER_ERROR
    return HttpStatus.valueOf(statusCode)
}

/**
 * @author VincentLee
 */
fun HttpServletResponse.ok(content: Any) {
    this.writeJSON(RR.ok(content))
}

/**
 * @author VincentLee
 */
fun HttpServletResponse.error(status: HttpStatus, message: String?) {
    this.writeJSON(RR.status(status).message(message))
}

/**
 * @author VincentLee
 */
private fun HttpServletResponse.writeJSON(any: Any) {
    this.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
    this.characterEncoding = "UTF-8"
    this.writer.use { it.write(G.gson.toJson(any)) }
}

/**
 * 可能为空的字符串
 * @author VincentLee
 */
object OptionalString {
    fun ofEmptyable(str: String?): Optional<String> =
            if (str == null || str == "")
                Optional.empty()
            else
                Optional.ofNullable(str)
}

/**
 * 可能为空的集合
 * @author VincentLee
 */
object OptionalCollection {
    fun <T : Collection<Any>> ofEmptyable(c: T?): Optional<T> =
            if (c == null || c.isEmpty())
                Optional.empty()
            else
                Optional.ofNullable(c)
}

/**
 * Date to LocalDateTime
 * @author VincentLee
 */
fun Date.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
}

/**
 * LocalDateTime to Date
 * @author VincentLee
 */
fun LocalDateTime.toDate(): Date {
    return Date.from(this.toInstant(ZoneOffset.UTC))
}

/**
 * 否则使用{@link NoSuchElementException}抛出指定异常信息
 */
fun <T> Optional<T>.orElseThrow(message: String): T {
    return this.orElseThrow {
        throw NoSuchElementException(message)
    }
}

/**
 * 为所有对象附加并缓存logger
 */
val Any.logger: Logger
    get() {
        val kClass = this::class
        return loggerCache.getOrPut(kClass) {
            LoggerFactory.getLogger(kClass.java)
        }
    }
