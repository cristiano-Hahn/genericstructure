package com.projects.genericstructure.adapters.security

import com.projects.genericstructure.core.domain.user.UserRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import reactor.core.publisher.Mono
import java.util.Arrays

@Configuration
class SpringSecurityConfiguration {

    companion object {
        val ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = Arrays.asList(
            "/users/login",
            "/users"
        ).toTypedArray()
    }

    @Bean
    fun springWebFilterChain(
        http: ServerHttpSecurity,
        tokenProvider: JwtTokenProvider,
        reactiveAuthenticationManager: ReactiveAuthenticationManager?
    ): SecurityWebFilterChain {

        return http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .authenticationManager(reactiveAuthenticationManager)
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authorizeExchange { it: AuthorizeExchangeSpec ->
                it.pathMatchers(*ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterAt(JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
            .build()
    }

    @Bean
    fun userDetailsService(users: UserRepository): ReactiveUserDetailsService {
        return ReactiveUserDetailsService { username: String ->
            Mono.defer {
                mono { users.findByEmail(username) }
                    .map { u ->
                        User.withUsername(u.email)
                            .password(u.password)
                            .authorities(*u.roles.map { it.name }.toTypedArray())
                            .accountExpired(!u.enabled)
                            .credentialsExpired(!u.enabled)
                            .disabled(!u.enabled)
                            .accountLocked(!u.enabled)
                            .build()
                    }
            }
        }
    }

    @Bean
    fun reactiveAuthenticationManager(
        userDetailsService: ReactiveUserDetailsService?,
        passwordEncoder: PasswordEncoder?
    ): ReactiveAuthenticationManager {
        val authenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
        authenticationManager.setPasswordEncoder(passwordEncoder)
        return authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
