package com.projects.genericstructure.adapters.security

import com.projects.genericstructure.core.domain.User
import java.util.UUID

object CustomUserDetailsServiceTestFixture {

    private const val USER_ID = "e070977b-93e4-4226-b53b-dbda6a36f0b3"
    const val USER_EMAIL = "user@test.com"

    fun user() = User(
        id = UUID.fromString(USER_ID),
        email = USER_EMAIL,
        password = "123",
        enabled = true,
        roles = listOf("USER")
    )

    fun expectedUserDetailsUser() = org.springframework.security.core.userdetails.User
        .withUsername(USER_EMAIL)
        .password("123")
        .roles("USER")
        .disabled(false)
        .build()
}
