package com.projects.genericstructure.adapters.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.Base64
import java.util.Date
import java.util.stream.Collectors
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {

    private lateinit var secretKey: SecretKey

    companion object {
        private const val AUTHORITIES_KEY = "roles"
        val LOGGER = KotlinLogging.logger { }
    }

    init {
        val secret = Base64.getEncoder().encodeToString(jwtProperties.secretKey.toByteArray())
        this.secretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    }

    fun createToken(authentication: Authentication): String {
        val claimsBuilder = Jwts.claims().subject(authentication.name)
        if (!authentication.authorities.isEmpty()) {
            claimsBuilder.add(
                AUTHORITIES_KEY,
                authentication.authorities.stream().map { it.authority }.collect(Collectors.joining(","))
            )
        }

        val claims = claimsBuilder.build()

        val now = Instant.now()
        val validity = now.plusMillis(jwtProperties.validityInMs)

        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date.from(now))
            .expiration(Date.from(validity))
            .signWith(this.secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val claims: Claims = Jwts.parser().verifyWith(this.secretKey).build()
            .parseSignedClaims(token).payload

        val authoritiesClaim = claims[AUTHORITIES_KEY]

        val authorities: Collection<GrantedAuthority> = if (authoritiesClaim == null)
            AuthorityUtils.NO_AUTHORITIES
        else
            AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString())

        val principal = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parser().verifyWith(this.secretKey)
                .build().parseSignedClaims(token)

            return true
        } catch (e: Exception) {
            LOGGER.warn("Invalid JWT token: ${e.message}")
        }
        return false
    }
}
