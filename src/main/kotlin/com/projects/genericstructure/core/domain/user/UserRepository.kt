package com.projects.genericstructure.core.domain.user

interface UserRepository {

    suspend fun create(user: User)

    suspend fun findByEmail(email: String): User?
}
