package com.projects.genericstructure.core.service.user.command

data class AuthenticateUserCommand(
    val email: String,
    val password: String
)
