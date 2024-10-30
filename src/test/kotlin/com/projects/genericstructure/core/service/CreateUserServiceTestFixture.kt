package com.projects.genericstructure.core.service

import com.projects.genericstructure.core.domain.user.Role
import com.projects.genericstructure.core.domain.user.User
import java.util.UUID

object CreateUserServiceTestFixture {

    private const val USER_ID = "e070977b-93e4-4226-b53b-dbda6a36f0b3"
    private const val USER_EMAIL = "user@test.com"

    fun user() = User(
        id = UUID.fromString(USER_ID),
        email = USER_EMAIL,
        password = "123",
        enabled = true,
        roles = listOf(Role.USER)
    )

    fun createUserCommand() = CreateUserCommand(
        id = UUID.fromString(USER_ID),
        email = USER_EMAIL,
        password = "123"
    )
}
