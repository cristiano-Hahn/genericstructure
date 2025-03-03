package com.projects.genericstructure.core.service

import com.projects.genericstructure.core.domain.company.Company
import com.projects.genericstructure.core.domain.user.User
import com.projects.genericstructure.core.service.user.command.CreateUserCommand
import java.time.Instant
import java.util.Optional
import java.util.UUID

object UserServiceTestFixture {

    val FIXED_UUID: UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
    val FIXED_INSTANT: Instant = Instant.parse("2024-01-01T12:00:00Z")
    private const val USER_EMAIL = "user@test.com"

    fun userCreated() = Optional.of(
        User(
            id = FIXED_UUID,
            email = USER_EMAIL,
            password = "123",
            enabled = false,
            roles = listOf(User.Role.USER),
            phone = "123",
            company = company(),
            createdAt = FIXED_INSTANT
        )
    )

    fun userEnabled() = Optional.of(userCreated().get().copy(enabled = true))

    fun userDisabled() = Optional.of(userCreated().get().copy(enabled = false))

    fun createUserCommand() = CreateUserCommand(
        id = FIXED_UUID,
        email = USER_EMAIL,
        password = "123",
        company = CreateUserCommand.Company(
            id = FIXED_UUID,
            name = "Empresa Exemplo",
            address = "Rua Fictícia, 123",
            documentNumber = "12345678000199",
            documentType = Company.DocumentType.CNPJ,
        ),
        phone = "123"
    )

    fun company(): Company {
        return Company(
            id = FIXED_UUID,
            name = "Empresa Exemplo",
            enabled = true,
            address = "Rua Fictícia, 123",
            documentNumber = "12345678000199",
            documentType = Company.DocumentType.CNPJ,
            createdAt = FIXED_INSTANT
        )
    }
}
