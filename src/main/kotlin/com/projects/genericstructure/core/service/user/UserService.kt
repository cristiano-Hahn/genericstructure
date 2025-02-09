package com.projects.genericstructure.core.service.user

import com.projects.genericstructure.core.domain.user.Role
import com.projects.genericstructure.core.domain.user.User
import com.projects.genericstructure.core.domain.user.UserEmailAlreadyExistsException
import com.projects.genericstructure.core.domain.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    suspend fun create(command: CreateUserCommand): User {
        validateUserEmailAlreadyExists(command.email)

        val user = User(
            id = command.id,
            email = command.email,
            password = passwordEncoder.encode(command.password),
            enabled = false,
            roles = listOf(Role.USER)
        )

        userRepository.create(user)

        return user
    }

    private suspend fun validateUserEmailAlreadyExists(email: String) {
        val user = userRepository.findByEmail(email)
        if (user != null) {
            throw UserEmailAlreadyExistsException(email)
        }
    }
}
