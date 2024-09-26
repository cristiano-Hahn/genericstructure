package com.projects.genericstructure.core.domain

interface UserRepository {

    suspend fun findByEmail(email: String): User?

}