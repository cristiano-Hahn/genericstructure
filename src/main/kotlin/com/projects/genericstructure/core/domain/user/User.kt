package com.projects.genericstructure.core.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "\"user\"")
data class User(

    @Id
    val id: UUID? = null,

    @Column
    val companyId: UUID,

    @Column(unique = true)
    val email: String,

    @Column
    val password: String,

    @Column
    val enabled: Boolean,

    @Column
    val phone: String,

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "text[]")
    val roles: List<Role>,

    val createdAt: Instant = Instant.now(),
) {
    enum class Role {
        ADMIN, USER_ADMIN, USER
    }

    constructor() : this(
        id = null,
        companyId = UUID.randomUUID(),
        email = "",
        password = "",
        enabled = false,
        phone = "",
        roles = listOf(),
        createdAt = Instant.now()
    )
}
