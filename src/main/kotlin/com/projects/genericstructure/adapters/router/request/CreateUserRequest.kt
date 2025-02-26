package com.projects.genericstructure.adapters.router.request

import com.projects.genericstructure.core.domain.company.Company.DocumentType
import com.projects.genericstructure.core.service.user.command.CreateUserCommand
import java.util.UUID

data class CreateUserRequest(
    val email: String,
    val password: String,
    val phone: String,
    val company: Company
) {
    data class Company(
        val name: String,
        val address: String?,
        val documentNumber: String?,
        val documentType: DocumentType?,
    )
}

fun CreateUserRequest.toCommand(
    userId: UUID,
    companyId: UUID
) = CreateUserCommand(
    id = userId,
    email = this.email,
    password = this.password,
    phone = this.phone,
    company = CreateUserCommand.Company(
        id = companyId,
        name = this.company.name,
        address = this.company.address,
        documentNumber = this.company.documentNumber,
        documentType = this.company.documentType,
    )
)
