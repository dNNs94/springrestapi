package com.example.springrestapi.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import java.lang.Exception

@Configuration
class ApplicationSecurity: WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/**")
    }
}