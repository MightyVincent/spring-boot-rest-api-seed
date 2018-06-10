package com.company.project.configurer

import com.company.project.common.enum.RoleEnum
import com.company.project.core.error
import com.company.project.core.ok
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
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
                .withUser("admin").password(encoder.encode("123")).roles(RoleEnum.ADMIN.name, RoleEnum.USER.name).and()
                .withUser("user").password(encoder.encode("123")).roles(RoleEnum.USER.name)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                // 匿名访问权限不足
                .authenticationEntryPoint(AuthenticationEntryPoint { _, response, authException ->
                    response.error(HttpStatus.FORBIDDEN, authException.message)
                })
                // 已授权访问权限不足
                .accessDeniedHandler(AccessDeniedHandler { _, response, accessDeniedException ->
                    response.error(HttpStatus.FORBIDDEN, accessDeniedException.message)
                })
                .and()
                // 授权
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler { _, response, authResult ->
                    response.ok(authResult.principal)
                }
                .failureHandler { _, response, authException ->
                    response.error(HttpStatus.BAD_REQUEST, authException.message)
                }
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole(RoleEnum.ADMIN.name)
                .antMatchers("/user/**").hasAnyRole(RoleEnum.ADMIN.name, RoleEnum.USER.name)
                .anyRequest().permitAll()
    }

}

