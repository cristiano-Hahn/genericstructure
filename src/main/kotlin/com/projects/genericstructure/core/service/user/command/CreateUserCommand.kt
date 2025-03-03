package com.projects.genericstructure.core.service.user.command

import com.projects.genericstructure.core.domain.company.Company
import com.projects.genericstructure.core.domain.company.Company.DocumentType
import com.projects.genericstructure.core.domain.user.User
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Clock
import java.time.Instant
import java.util.UUID

data class CreateUserCommand(
    val id: UUID,
    val email: String,
    val password: String,
    val phone: String,
    val company: Company
) {
    data class Company(
        val id: UUID,
        val name: String,
        val address: String?,
        val documentNumber: String?,
        val documentType: DocumentType?,
    )
}

fun CreateUserCommand.toUser(passwordEncoder: PasswordEncoder, clock: Clock) = User(
    id = this.id,
    company = Company(
        id = this.company.id,
        name = this.company.name,
        enabled = true,
        address = this.company.address,
        documentType = this.company.documentType,
        documentNumber = this.company.documentNumber,
        createdAt = Instant.now(clock)
    ),
    email = this.email,
    password = passwordEncoder.encode(this.password),
    enabled = false,
    roles = listOf(User.Role.USER),
    phone = this.phone,
    createdAt = Instant.now(clock)
)
