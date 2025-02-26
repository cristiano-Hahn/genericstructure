package com.projects.genericstructure.core.service.user.command

import com.projects.genericstructure.core.domain.company.Company
import com.projects.genericstructure.core.domain.company.Company.DocumentType
import com.projects.genericstructure.core.domain.user.User
import java.time.Instant
import java.util.UUID

data class CreateUserCommandResult(
    val id: UUID,
    val email: String,
    val password: String,
    val phone: String,
    val company: Company,
    val enabled: Boolean,
    val roles: Set<String>,
    val createdAt: Instant,
) {
    data class Company(
        val id: UUID,
        val enabled: Boolean,
        val name: String,
        val address: String?,
        val documentNumber: String?,
        val documentType: DocumentType?,
    )
}

fun User.toCreateUserCommandResult(company: Company) = CreateUserCommandResult(
    id = this.id!!,
    email = this.email,
    password = this.password,
    phone = this.phone,
    enabled = this.enabled,
    roles = this.roles.map { it.name }.toSet(),
    createdAt = this.createdAt,
    company = CreateUserCommandResult.Company(
        id = company.id!!,
        enabled = company.enabled,
        name = company.name,
        address = company.address,
        documentNumber = company.documentNumber,
        documentType = company.documentType,
    )
)
