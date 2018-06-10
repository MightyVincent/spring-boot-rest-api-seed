package com.company.project.core

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
    this.writer.use { it.write(GB.gson.toJson(any)) }
}

/**
 * @author VincentLee
 */
object OptionalString {
    fun ofEmpty(str: String?): Optional<String> =
            if (str == null || str == "")
                Optional.empty()
            else
                Optional.ofNullable(str)
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