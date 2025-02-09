package com.projects.genericstructure.core.domain.user

import java.util.UUID

abstract class UserException(message: String) : RuntimeException(message)

class UserEmailAlreadyExistsException(val email: String) : UserException("User with email $email already exists")

class UserNotFoundException(val id: UUID) : UserException("User with id $id could not be found")
