package com.company.project.core.security

import com.company.project.common.constant.ProjectProperties
import com.company.project.utils.LocalTimeUtil
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.net.URLDecoder
import java.net.URLEncoder
import java.time.LocalDateTime
import java.util.*
import javax.annotation.Resource
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenHelper {

    @Resource
    fun setProjectProperties(projectProperties: ProjectProperties) {
        JwtTokenHelper.projectProperties = projectProperties
    }

    companion object {
        private lateinit var projectProperties: ProjectProperties

        private const val TOKEN_PREFIX = "Bearer "
        private const val HEADER = "Authorization"
        private val EXPIRATION: Date = LocalTimeUtil.from(LocalDateTime.now().plusDays(1000))

        private fun build(subject: String): String {

            val token = Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(EXPIRATION)
                    .signWith(SignatureAlgorithm.HS512, projectProperties.jwtSecret)
                    .compact()

            return TOKEN_PREFIX + token
        }

        fun buildHeader(response: HttpServletResponse, auth: Authentication) {
            val token = build(auth.principal.toString())
            response.addHeader(HEADER, token)
            response.addCookie(Cookie(HEADER, URLEncoder.encode(HEADER, "UTF-8")))
            response.addHeader("Access-Control-Expose-Headers", HEADER)
        }

        private fun parse(header: String): String {
            return Jwts.parser()
                    .setSigningKey(projectProperties.jwtSecret)
                    .parseClaimsJws(header.replaceFirst(TOKEN_PREFIX.toRegex(), ""))
                    .body
                    .subject
        }

        fun parse(request: HttpServletRequest): Subject {
            var header = request.getHeader(HEADER)
            if (StringUtils.isEmpty(header) && request.cookies != null) {
                for (cookie in request.cookies) {
                    if (HEADER == cookie.name) {
                        header = URLDecoder.decode(cookie.value, "UTF-8")
                        break
                    }
                }
            }
            return Subject(
                    if (StringUtils.isEmpty(header) || !header.startsWith(TOKEN_PREFIX))
                        null
                    else
                        parse(header.replaceFirst(TOKEN_PREFIX.toRegex(), ""))
            )
        }

        class Subject(private val subject: String?) {
            private val isEmpty: Boolean = StringUtils.isEmpty(subject)

            fun ifPresent(block: (String) -> Unit): Subject {
                if (!isEmpty) {
                    block(subject as String)
                }
                return this
            }

            fun orElse(block: () -> Unit) {
                if (isEmpty) {
                    block()
                }
            }
        }
    }

}
