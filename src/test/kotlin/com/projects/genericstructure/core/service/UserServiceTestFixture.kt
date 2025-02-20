package com.projects.genericstructure.core.service

import com.projects.genericstructure.core.domain.user.Role
import com.projects.genericstructure.core.domain.user.User
import com.projects.genericstructure.core.service.user.command.CreateUserCommand
import java.util.UUID

object UserServiceTestFixture {

    val USER_ID = UUID.fromString("e070977b-93e4-4226-b53b-dbda6a36f0b3")
    private const val USER_EMAIL = "user@test.com"

    fun userCreated() = User(
        id = USER_ID,
        email = USER_EMAIL,
        password = "123",
        enabled = false,
        roles = listOf(Role.USER)
    )

    fun userEnabled() = userCreated().copy(enabled = true)

    fun userDisabled() = userCreated().copy(enabled = false)

    fun createUserCommand() = CreateUserCommand(
        id = USER_ID,
        email = USER_EMAIL,
        password = "123"
    )
}
