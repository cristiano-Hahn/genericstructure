package com.projects.genericstructure.adapters.r2dbc

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.r2dbc.core.DatabaseClient

object DatabaseClientFixture {
    fun databaseClient(): DatabaseClient {
        val factory = PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .username("generic_structure_user")
                .password("generic_structure_password")
                .database("generic_structure_db")
                .build()
        )

        return DatabaseClient.builder().connectionFactory(factory).build()
    }
}
