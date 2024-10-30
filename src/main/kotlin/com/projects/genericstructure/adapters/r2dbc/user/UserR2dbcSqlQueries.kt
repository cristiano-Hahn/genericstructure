package com.projects.genericstructure.adapters.r2dbc.user

object UserR2dbcSqlQueries {

    fun findUserByEmail() = """
        select 
            id, 
            email, 
            password,
            enabled,
            roles
        from "user"
        where email = :email
    """.trimIndent()

    fun createUser() = """
        INSERT INTO "user" (id, email, password, roles, enabled)
        VALUES (:id, :email, :password, :roles, :enabled);
    """.trimIndent()
}
