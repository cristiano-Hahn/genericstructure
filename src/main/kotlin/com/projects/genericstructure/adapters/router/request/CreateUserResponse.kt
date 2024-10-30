package com.projects.genericstructure.adapters.router.request

import com.projects.genericstructure.core.domain.user.User

data class CreateUserResponse(
    val id: String,
    val email: String,
    val roles: List<String>
)

fun User.toCreateUserResponse() = CreateUserResponse(
    id = this.id.toString(),
    email = this.email,
    roles = this.roles.map { it.toString() }
)
