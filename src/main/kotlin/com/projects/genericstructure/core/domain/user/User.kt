package com.projects.genericstructure.core.domain.user

import com.projects.genericstructure.core.domain.company.Company
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    val company: Company,

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
        company = Company(),
        email = "",
        password = "",
        enabled = false,
        phone = "",
        roles = listOf(),
        createdAt = Instant.now()
    )
}
