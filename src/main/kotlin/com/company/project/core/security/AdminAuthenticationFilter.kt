package com.company.project.core.security

import com.alibaba.fastjson.JSON
import com.company.project.core.ResultGenerator
import org.apache.commons.io.IOUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AdminAuthenticationFilter(manager: AuthenticationManager, pattern: String) : UsernamePasswordAuthenticationFilter() {

    init {
        this.setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher(pattern, "POST"))
        this.authenticationManager = manager
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain?, authResult: Authentication) {
        JwtTokenHelper.buildHeader(response, authResult)

        response.outputStream.use { os ->
            IOUtils.write(JSON.toJSONString(ResultGenerator.genSuccessResult()),
                    os, "UTF-8")
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException) {
        response.outputStream.use { os ->
            IOUtils.write(JSON.toJSONString(ResultGenerator.genFailResult("登录失败，用户名或密码错误！")),
                    os, "UTF-8")
        }
    }
}
