package com.projects.genericstructure.adapters.r2dbc.user

import com.projects.genericstructure.core.domain.user.Role
import com.projects.genericstructure.core.domain.user.User
import java.util.UUID

object UserR2dbcRepositoryIntegrationTestFixture {

    private const val USER_ID = "e070977b-93e4-4226-b53b-dbda6a36f0b3"
    const val USER_EMAIL = "user@test.com"
    const val USER_EMAIL_UPDATED = "updated@test.com"
    const val USER_INVALID_EMAIL = "asdijaod@test.com"

    fun sqlMockUser() = """
        insert into "user"(id, email, password, enabled, roles) 
        values ('$USER_ID', '$USER_EMAIL', '123', true, array['USER']);
    """.trimIndent()

    fun sqlUnmockUser() = """
        delete from "user" where id = '$USER_ID';
    """.trimIndent()

    fun expectedUser() = User(
        id = UUID.fromString(USER_ID),
        email = USER_EMAIL,
        password = "123",
        enabled = true,
        roles = listOf(Role.USER)
    )

    fun userUpdated() = expectedUser().copy(
        email = USER_EMAIL_UPDATED,
        password = "1234",
        enabled = false,
        roles = listOf(Role.ADMIN)
    )
}
