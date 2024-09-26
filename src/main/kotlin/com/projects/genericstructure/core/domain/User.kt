package com.projects.genericstructure.core.domain

import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val password: String
)