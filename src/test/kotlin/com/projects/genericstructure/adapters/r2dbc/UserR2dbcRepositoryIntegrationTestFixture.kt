package com.projects.genericstructure.adapters.r2dbc

import com.projects.genericstructure.core.domain.User
import java.util.*

object UserR2dbcRepositoryIntegrationTestFixture {

    private const val USER_ID = "e070977b-93e4-4226-b53b-dbda6a36f0b3"
    const val USER_EMAIL = "test@test.com"
    const val USER_INVALID_EMAIL = "asdijaod@test.com"

    fun sqlMockUser() = """
        insert into "user"(id, email, password) 
        values ('$USER_ID', '$USER_EMAIL', '123');
    """.trimIndent()

    fun sqlUnmockUser() = """
        delete from "user" where id = '$USER_ID';
    """.trimIndent()

    fun expectedUser() = User(
        id = UUID.fromString(USER_ID),
        email = USER_EMAIL,
        password = "123"
    )
}
