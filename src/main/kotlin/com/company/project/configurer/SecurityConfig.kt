package com.company.project.configurer

import com.alibaba.fastjson.JSON
import org.apache.commons.io.IOUtils
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

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
        val loginProcessUrl = "/api/login"
//        val loginProcessUrl = "/login"
        val authenticationFilter = object : UsernamePasswordAuthenticationFilter() {
            init {
                this.authenticationManager = authenticationManager()
            }

            override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
                super.successfulAuthentication(request, response, chain, authResult)
            }

            override fun unsuccessfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, failed: AuthenticationException?) {
                super.unsuccessfulAuthentication(request, response, failed)
            }
        }
        authenticationFilter.setAuthenticationManager(this.authenticationManager())
        authenticationFilter.setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher(loginProcessUrl, "POST"))
        /*val authenticationFilter = AdminAuthenticationFilter(authenticationManager(), loginProcessUrl)*/
        val handleAuthException = { _: HttpServletRequest,
                                    response: HttpServletResponse, authException: AuthenticationException ->
            response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
            response.outputStream.use { os ->
                IOUtils.write(JSON.toJSONString(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authException.message)),
                        os, "UTF-8")
            }
        }
        http
                .antMatcher("/api/**")
                .csrf().disable()
                .cors()
                .and()
//                .loginProcessingUrl(loginProcessUrl)
//                .successHandler({ request, response, authResult ->
//
//                    JwtTokenHelper.buildHeader(response, authResult)
//
//                    response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
//                    response.outputStream.use { os ->
//                        IOUtils.write(JSON.toJSONString(ResponseEntity.ok(authResult.principal)), os, "UTF-8")
//                    }
//                })
//                .failureHandler(handleAuthException)
//                .and()
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
                /*.addFilterAfter(AdminAuthorizationFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter::class.java)*/
                .authorizeRequests()
                .antMatchers(loginProcessUrl).permitAll()
                .anyRequest().authenticated()
    }
}
