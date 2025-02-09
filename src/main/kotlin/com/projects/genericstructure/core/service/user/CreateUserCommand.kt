package com.projects.genericstructure.core.service.user

import java.util.UUID

data class CreateUserCommand(
    val id: UUID,
    val email: String,
    val password: String
)
