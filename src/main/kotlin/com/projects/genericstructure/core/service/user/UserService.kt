package com.projects.genericstructure.core.service.user

import com.projects.genericstructure.core.domain.user.Role
import com.projects.genericstructure.core.domain.user.User
import com.projects.genericstructure.core.domain.user.UserEmailAlreadyExistsException
import com.projects.genericstructure.core.domain.user.UserNotFoundException
import com.projects.genericstructure.core.domain.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

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

    suspend fun enable(userId: UUID) {
        val user = userRepository.findById(userId) ?: throw UserNotFoundException(userId)

        if (user.enabled) {
            return
        }

        val updatedUser = user.copy(enabled = true)
        userRepository.update(userId, updatedUser)
    }

    suspend fun disable(userId: UUID) {
        val user = userRepository.findById(userId) ?: throw UserNotFoundException(userId)

        if (!user.enabled) {
            return
        }

        val updatedUser = user.copy(enabled = false)
        userRepository.update(userId, updatedUser)
    }

    private suspend fun validateUserEmailAlreadyExists(email: String) {
        val user = userRepository.findByEmail(email)
        if (user != null) {
            throw UserEmailAlreadyExistsException(email)
        }
    }
}
