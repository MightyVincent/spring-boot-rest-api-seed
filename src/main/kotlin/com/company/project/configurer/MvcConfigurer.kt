package com.company.project.configurer

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import com.company.project.core.Result
import com.company.project.core.ResultCode
import com.company.project.core.ServiceException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.IOException
import java.nio.charset.Charset
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Spring MVC 配置
 */
@Configuration
class MvcConfigurer : WebMvcConfigurer {

    private val logger = LoggerFactory.getLogger(MvcConfigurer::class.java)
    @Value("\${spring.profiles.active}")
    private val env: String? = null//当前激活的配置文件


    //使用阿里 FastJson 作为JSON MessageConverter
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val converter = FastJsonHttpMessageConverter()
        val config = FastJsonConfig()
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, //保留空的字段
                SerializerFeature.WriteNullStringAsEmpty, //String null -> ""
                SerializerFeature.WriteNullNumberAsZero)//Number null -> 0
        converter.fastJsonConfig = config
        converter.defaultCharset = Charset.forName("UTF-8")
        converters.add(converter)
    }


    //统一异常处理
    override fun configureHandlerExceptionResolvers(exceptionResolvers: MutableList<HandlerExceptionResolver>) {
        exceptionResolvers.add(HandlerExceptionResolver(function = {
            request, response, handler, e ->
            val result = Result()
            if (e is ServiceException) {//业务失败的异常，如“账号或密码错误”
                result.setCode(ResultCode.FAIL).message = e.message
                logger.info(e.message)
            } else if (e is NoHandlerFoundException) {
                result.setCode(ResultCode.NOT_FOUND).message = "接口 [" + request.requestURI + "] 不存在"
            } else if (e is ServletException) {
                result.setCode(ResultCode.FAIL).message = e.message
            } else {
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR).message = "接口 [" + request.requestURI + "] 内部错误，请联系管理员"
                val message: String?
                if (handler is HandlerMethod) {
                    val handlerMethod = handler
                    message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                            request.requestURI,
                            handlerMethod.bean.javaClass.name,
                            handlerMethod.method.name,
                            e.message)
                } else {
                    message = e.message
                }
                logger.error(message, e)
            }
            responseResult(response, result)
            ModelAndView()
        }))
    }

    //解决跨域问题
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
    }

    private fun responseResult(response: HttpServletResponse, result: Result) {
        response.characterEncoding = "UTF-8"
        response.setHeader("Content-type", "application/json;charset=UTF-8")
        response.status = 200
        try {
            response.writer.write(JSON.toJSONString(result))
        } catch (ex: IOException) {
            logger.error(ex.message)
        }

    }

    private fun getIpAddress(request: HttpServletRequest): String? {
        var ip: String? = request.getHeader("x-forwarded-for")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_CLIENT_IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim { it <= ' ' }
        }

        return ip
    }
}
