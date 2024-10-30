package com.projects.genericstructure.core.domain.user

abstract class UserException(message: String) : RuntimeException(message)

class UserEmailAlreadyExistsException(val email: String) : UserException("User with email $email already exists")
