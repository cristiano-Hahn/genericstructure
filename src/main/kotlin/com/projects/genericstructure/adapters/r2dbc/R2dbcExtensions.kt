package com.projects.genericstructure.adapters.r2dbc

import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T> Row.get(identifier: String): T =
    this.get(identifier, T::class.java)!!

inline fun <reified T> Row.getOrNull(identifier: String): T? =
    this.get(identifier, T::class.java)

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindOrNull(name: String, value: T?) =
    value?.let { this.bind(name, it as Any) } ?: this.bindNull(name, T::class.java)

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindIfNotNull(name: String, value: T?) =
    value?.let { this.bind(name, it as Any) } ?: this

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindIfNotEmpty(name: String, value: Collection<T>) =
    value.takeIf { it.isNotEmpty() }?.let { this.bind(name, it as Any) } ?: this
