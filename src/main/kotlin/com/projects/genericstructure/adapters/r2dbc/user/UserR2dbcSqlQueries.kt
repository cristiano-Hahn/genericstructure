package com.projects.genericstructure.adapters.r2dbc.user

object UserR2dbcSqlQueries {

    fun findUserByEmail() = """
        select 
            id, 
            email, 
            password 
        from "user"
        where email = :email
    """.trimIndent()
}