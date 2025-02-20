package com.projects.genericstructure.core.service.user.command

import java.util.UUID

data class CreateUserCommand(
    val id: UUID,
    val email: String,
    val password: String
)
