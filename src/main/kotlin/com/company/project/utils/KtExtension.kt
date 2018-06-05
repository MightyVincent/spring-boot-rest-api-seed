package com.company.project.utils

import com.alibaba.fastjson.JSON
import org.apache.commons.io.IOUtils
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * @author VincentLee
 */
fun HttpServletResponse.writeJSON(entity: ResponseEntity<*>) {
    this.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
    this.outputStream.use { IOUtils.write(JSON.toJSONString(entity), it, "UTF-8") }
}

/**
 * @author VincentLee
 */
fun HttpServletRequest.isAjaxRequest(): Boolean =
        "XMLHttpRequest" == this.getHeader("X-Requested-With")

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
