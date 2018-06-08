package com.company.project.configurer

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
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler

/**
 * @author VincentLee
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/public/**", "/webjars/**")
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
                .anonymous().disable()
                .csrf().disable()
                .cors()
                .and()
                .exceptionHandling()
                // 匿名访问权限不足
                .authenticationEntryPoint(AuthenticationEntryPoint { _, response, authException ->
                    response.writeJSON(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authException.message))
                })
                // 已认证访问权限不足
                .accessDeniedHandler(AccessDeniedHandler { _, response, accessDeniedException ->
                    response.writeJSON(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(accessDeniedException.message))
                })
                .and()
                // 授权
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler { _, response, authResult ->
                    response.writeJSON(ResponseEntity.ok(authResult.principal))
                }
                .failureHandler { _, response, authException ->
                    response.writeJSON(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authException.message))
                }
                .and()
                .authorizeRequests()
                .antMatchers("/api/u/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/a/**").hasRole("ADMIN")
                .anyRequest().permitAll()
    }

}

