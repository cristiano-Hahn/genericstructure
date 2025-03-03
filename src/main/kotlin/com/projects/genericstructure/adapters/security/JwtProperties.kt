package com.projects.genericstructure.adapters.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String = "rzxlszyykpbgqcflzxsqcysyhljt",
    val validityInMs: Long = 3600000 // 1h
)
