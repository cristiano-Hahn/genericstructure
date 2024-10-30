package com.projects.genericstructure.adapters.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.projects.genericstructure.adapters.router.ErrorResponseBody
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SpringSecurityConfiguration {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/actuator/**", "/users/**").permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic { it }
            .exceptionHandling {
                it
                    .authenticationEntryPoint { _, response, authException ->
                        response.status = HttpStatus.UNAUTHORIZED.value()
                        response.addHeader("Content-Type", "application/json")
                        response.writer.write(authException.message!!.toResponseBodyError())
                    }
                    .accessDeniedHandler { _, response, accessDeniedException ->
                        response.status = HttpStatus.FORBIDDEN.value()
                        response.addHeader("Content-Type", "application/json")
                        response.writer.write(accessDeniedException.message!!.toResponseBodyError())
                    }
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun String.toResponseBodyError() =
        jacksonObjectMapper().writeValueAsString(ErrorResponseBody(this))
}
