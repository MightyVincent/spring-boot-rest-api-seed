package com.company.project.utils

import com.alibaba.fastjson.JSON
import org.apache.commons.io.IOUtils
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


fun HttpServletResponse.writeJSON(entity: ResponseEntity<*>) {
    this.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
    this.outputStream.use { IOUtils.write(JSON.toJSONString(entity), it, "UTF-8") }
}

fun HttpServletRequest.isAjaxRequest(): Boolean =
        "XMLHttpRequest" == this.getHeader("X-Requested-With")

object OptionalString {
    fun ofEmpty(str: String?): Optional<String> =
            if (str == null || str == "")
                Optional.empty()
            else
                Optional.ofNullable(str)
}
