package com.projects.genericstructure.adapters.router.request

import com.projects.genericstructure.core.service.CreateUserCommand
import java.util.UUID

data class CreateUserRequest(
    val email: String,
    val password: String
)

fun CreateUserRequest.toCommand(userId: UUID) = CreateUserCommand(
    id = userId,
    email = this.email,
    password = this.password,
)
