package com.projects.genericstructure.core.domain.company

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "company")
data class Company(

    @Id
    val id: UUID? = null,

    @Column
    val name: String,

    @Column
    val enabled: Boolean,

    val address: String?,

    @Column
    val documentNumber: String?,

    @Enumerated(EnumType.STRING)
    @Column
    val documentType: DocumentType?,

    @Column
    val createdAt: Instant = Instant.now(),
) {

    enum class DocumentType {
        CPF, CNPJ
    }

    constructor() : this(
        id = null,
        name = "",
        enabled = true,
        createdAt = Instant.now(),
        address = "",
        documentNumber = "",
        documentType = DocumentType.CPF
    )
}
