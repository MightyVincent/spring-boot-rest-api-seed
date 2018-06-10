package com.company.project.core

import com.company.project.Application
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/**
 * @author VincentLee
 */
@ControllerAdvice(basePackageClasses = [(Application::class)])
class AppCtrAdvice {
    private val logger = LoggerFactory.getLogger(AppCtrAdvice::class.java)

    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun handleControllerException(request: HttpServletRequest, ex: Throwable): RR<Void> {
        when (ex) {
            is ServiceException -> logger.info(ex.message)
            else -> logger.error("", ex)
        }
        val status = request.getStatus()
        return RR.status(status).message(ex.message)
    }

}