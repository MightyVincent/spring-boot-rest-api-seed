package com.company.project.core.security

import com.company.project.common.constant.ProjectProperties
import com.company.project.common.enums.RoleEnum
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.Resource

@Component
class AdminAuthenticationProvider : AuthenticationProvider {

    @Resource
    private lateinit var projectProperties: ProjectProperties

    private lateinit var encoder: BCryptPasswordEncoder

    private lateinit var encodedPassword: String

    @PostConstruct
    private fun init() {
        encoder = BCryptPasswordEncoder(4)
        encodedPassword = encoder.encode(projectProperties.adminPassword)
    }

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        return if (projectProperties.adminUsername == authentication.principal
                && encoder.matches(authentication.credentials.toString(), encodedPassword)) {
            UsernamePasswordAuthenticationToken(projectProperties.adminUsername, null,
                    listOf(SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.name)))
        } else null

    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
