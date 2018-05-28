package com.company.project.core.security

import com.alibaba.fastjson.JSON
import com.company.project.common.enums.RoleEnum
import com.company.project.core.ResultCode
import com.company.project.core.ResultGenerator
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AdminAuthorizationFilter(authenticationManager: AuthenticationManager?) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        JwtTokenHelper.parse(request)
                .ifPresent({ subject: String ->
                    SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(subject, null,
                            listOf(SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.name)))
                    chain.doFilter(request, response)
                })
                .orElse {
                    response.characterEncoding = "UTF-8"
                    response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
                    response.writer.use {
                        it.write(JSON.toJSONString(ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,
                                "认证失败！")))
                    }
                }

    }
}
