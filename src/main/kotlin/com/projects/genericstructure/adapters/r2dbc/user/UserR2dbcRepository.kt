package com.projects.genericstructure.adapters.r2dbc.user

import com.projects.genericstructure.adapters.r2dbc.get
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcSqlQueries.createUser
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcSqlQueries.findUserByEmail
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcSqlQueries.updateUser
import com.projects.genericstructure.core.domain.user.Role
import com.projects.genericstructure.core.domain.user.User
import com.projects.genericstructure.core.domain.user.UserRepository
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserR2dbcRepository(
    private val db: DatabaseClient,
) : UserRepository {

    override suspend fun findByEmail(email: String) =
        db
            .sql(findUserByEmail())
            .bind("email", email)
            .map { row, _ -> row.toUser() }
            .awaitOneOrNull()

    override suspend fun create(user: User) {
        db.sql(createUser())
            .bind("id", user.id)
            .bind("email", user.email)
            .bind("password", user.password)
            .bind("enabled", user.enabled)
            .bind("roles", user.roles.map { it.name }.toTypedArray())
            .await()
    }

    override suspend fun update(userId: UUID, user: User) {
        db.sql(updateUser())
            .bind("id", user.id)
            .bind("email", user.email)
            .bind("password", user.password)
            .bind("enabled", user.enabled)
            .bind("roles", user.roles.map { it.name }.toTypedArray())
            .await()
    }

    private fun Row.toUser() = User(
        id = this.get<UUID>("id"),
        email = this.get<String>("email"),
        password = this.get<String>("password"),
        enabled = this.get<Boolean>("enabled"),
        roles = this.get<Array<String>>("roles").map { Role.valueOf(it) },
    )
}
