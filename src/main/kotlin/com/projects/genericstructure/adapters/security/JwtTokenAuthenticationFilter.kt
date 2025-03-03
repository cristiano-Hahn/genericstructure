package com.projects.genericstructure.adapters.security

import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class JwtTokenAuthenticationFilter(private val tokenProvider: JwtTokenProvider) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = retrieveToken(exchange.request)
        if (token != null && tokenProvider.validateToken(token)) {
            return Mono.fromCallable { tokenProvider.getAuthentication(token) }
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap { authentication: Authentication ->
                    chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                }
        }
        return chain.filter(exchange)
    }

    private fun retrieveToken(request: org.springframework.http.server.reactive.ServerHttpRequest): String? {
        val bearerToken = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }
}
