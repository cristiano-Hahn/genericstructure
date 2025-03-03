package com.projects.genericstructure.adapters.router.request

import com.projects.genericstructure.core.domain.company.Company.DocumentType
import com.projects.genericstructure.core.service.user.command.CreateUserCommandResult
import com.projects.genericstructure.core.service.user.command.CreateUserCommandResult.Company
import java.time.Instant
import java.util.UUID

data class CreateUserResponse(
    val id: String,
    val email: String,
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

fun CreateUserCommandResult.toCreateUserResponse() = CreateUserResponse(
    id = this.id.toString(),
    email = this.email,
    phone = this.phone,
    enabled = this.enabled,
    roles = this.roles,
    createdAt = this.createdAt,
    company = CreateUserResponse.Company(
        id = company.id,
        enabled = company.enabled,
        name = company.name,
        address = company.address,
        documentNumber = company.documentNumber,
        documentType = company.documentType,
    )
)
