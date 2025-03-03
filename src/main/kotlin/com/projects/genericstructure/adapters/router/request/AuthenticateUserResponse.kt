package com.projects.genericstructure.adapters.router.request

data class AuthenticateUserResponse(
    val token: String
)

fun String.toAuthenticateUserResponse() = AuthenticateUserResponse(this)
