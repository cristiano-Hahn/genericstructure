package com.projects.genericstructure.adapters.router.request

import com.projects.genericstructure.core.service.user.command.AuthenticateUserCommand

data class AuthenticateUserRequest(
    val email: String,
    val password: String
)

fun AuthenticateUserRequest.toCommand() = AuthenticateUserCommand(email, password)
