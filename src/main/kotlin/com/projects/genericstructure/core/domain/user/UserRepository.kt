package com.projects.genericstructure.core.domain.user

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UserRepository : CrudRepository<User, UUID> {
    fun findByEmail(email: String): Optional<User>
}
