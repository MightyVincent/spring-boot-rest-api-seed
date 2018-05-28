package com.company.project.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 新旧时间API转换工具类
 *
 * @author limingcheng
 */
object LocalTimeUtil {

    /**
     * 获取当前年月日字符串
     */
    val currentDateString: String
        get() = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    /**
     * Date to LocalDateTime
     */
    fun from(date: Date): LocalDateTime {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
    }

    /**
     * LocalDateTime to Date
     */
    fun from(time: LocalDateTime): Date {
        return Date.from(time.toInstant(ZoneOffset.UTC))
    }
}
