package com.projects.genericstructure.core.domain.user

import java.util.UUID

interface UserRepository {

    suspend fun create(user: User)

    suspend fun update(userId: UUID, user: User)

    suspend fun findByEmail(email: String): User?

    suspend fun findById(id: UUID): User?
}
