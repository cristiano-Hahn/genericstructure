package com.projects.genericstructure.core.service.user

import com.projects.genericstructure.adapters.security.JwtTokenProvider
import com.projects.genericstructure.core.domain.user.UserEmailAlreadyExistsException
import com.projects.genericstructure.core.domain.user.UserNotFoundException
import com.projects.genericstructure.core.domain.user.UserPhoneAlreadyExistsException
import com.projects.genericstructure.core.domain.user.UserRepository
import com.projects.genericstructure.core.service.user.command.AuthenticateUserCommand
import com.projects.genericstructure.core.service.user.command.CreateUserCommand
import com.projects.genericstructure.core.service.user.command.CreateUserCommandResult
import com.projects.genericstructure.core.service.user.command.toCreateUserCommandResult
import com.projects.genericstructure.core.service.user.command.toUser
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Clock
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: JwtTokenProvider,
    private val authenticationManager: ReactiveAuthenticationManager,
    private val clock: Clock = Clock.systemDefaultZone()
) {

    fun create(command: CreateUserCommand): CreateUserCommandResult {
        validateUserEmailAlreadyExists(command.email)
        validateUserPhoneAlreadyExists(command.phone)

        val user = userRepository.save(command.toUser(passwordEncoder, clock))

        return user.toCreateUserCommandResult()
    }

    fun authenticate(command: AuthenticateUserCommand): String {
        val authenticationToken = UsernamePasswordAuthenticationToken(command.email, command.password)
        val authentication = authenticationManager.authenticate(authenticationToken).toFuture().get()
        return tokenProvider.createToken(authentication)
    }

    fun enable(userId: UUID) {
        val user = userRepository.findById(userId).getOrNull() ?: throw UserNotFoundException(userId)
        userRepository.save(user.copy(enabled = true))
    }

    fun disable(userId: UUID) {
        val user = userRepository.findById(userId).getOrNull() ?: throw UserNotFoundException(userId)
        userRepository.save(user.copy(enabled = false))
    }

    private fun validateUserEmailAlreadyExists(email: String) {
        val user = userRepository.findByEmail(email)
        if (user.isPresent) {
            throw UserEmailAlreadyExistsException(email)
        }
    }

    private fun validateUserPhoneAlreadyExists(phone: String) {
        val user = userRepository.findByPhone(phone)
        if (user.isPresent) {
            throw UserPhoneAlreadyExistsException(phone)
        }
    }
}
