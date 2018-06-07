package com.company.project.configurer

import com.company.project.utils.isAjaxRequest
import com.company.project.utils.writeJSON
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author VincentLee
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web
                .ignoring().antMatchers("/public/**", "/webjars/**")
                .and()
                .securityInterceptor(object: FilterSecurityInterceptor() {
                    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
                        try {
                            super.doFilter(request, response, chain)
                        } catch (e: Exception) {
                            if ((request as HttpServletRequest).isAjaxRequest()) {
                                (response as HttpServletResponse)
                                        .writeJSON(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message))
                            } else {
                                throw e
                            }
                        }
                    }
                })
    }

    override fun configure(builder: AuthenticationManagerBuilder) {
        val encoder = BCryptPasswordEncoder(4)
        builder
                .inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("admin").password(encoder.encode("123")).roles("ADMIN", "USER").and()
                .withUser("user").password(encoder.encode("123")).roles("USER")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .cors()
                .and()
                // 授权
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler({ _, response, authResult ->
                    response.writeJSON(ResponseEntity.ok(authResult.principal))
                })
                .failureHandler({ _, response, authException ->
                    response.writeJSON(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authException.message))
                })
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
    }

}

