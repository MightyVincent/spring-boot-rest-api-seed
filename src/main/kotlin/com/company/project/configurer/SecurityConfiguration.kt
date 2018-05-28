package com.company.project.configurer

import com.company.project.core.security.AdminAuthenticationFilter
import com.company.project.core.security.AdminAuthorizationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        web!!.ignoring().antMatchers("/public/**", "/webjars/**", "/swagger**")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .cors()
                .and()
                .antMatcher("/api/admin/**")
                .addFilterAt(AdminAuthenticationFilter(authenticationManager(), "/api/admin/login"),
                        UsernamePasswordAuthenticationFilter::class.java)
                .addFilterAfter(AdminAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers("/api/admin/login").permitAll()
                // 允许其它所有
                .anyRequest().authenticated()
    }
}
