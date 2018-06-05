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
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    val loginUrl = "/login"

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/public/**", "/webjars/**")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        val encoder = BCryptPasswordEncoder(4)
        auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("admin").password(encoder.encode("123")).roles("ADMIN", "USER").and()
                .withUser("user").password(encoder.encode("123")).roles("USER")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val handleAuthException = { _: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException ->
            response.writeJSON(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authException.message))
        }
        http
                .csrf().disable()
                .cors()
                .and()
                // 授权
                .formLogin()
                .loginPage(loginUrl)
                .loginProcessingUrl(loginUrl)
                .successHandler({ _, response, authResult ->
                    response.writeJSON(ResponseEntity.ok(authResult.principal))
                })
                .failureHandler(handleAuthException)
                .and()
                // 验证失败
                .exceptionHandling().authenticationEntryPoint(object : LoginUrlAuthenticationEntryPoint(loginUrl) {
                    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
                        if (request.isAjaxRequest()) {
                            handleAuthException(request, response, authException)
                        } else {
                            super.commence(request, response, authException)
                        }
                    }
                })
                .and()
                .authorizeRequests()
//                .antMatchers(loginProcessUrl).permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
    }

}

