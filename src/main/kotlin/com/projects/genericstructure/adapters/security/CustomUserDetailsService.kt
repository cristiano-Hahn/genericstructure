package com.projects.genericstructure.adapters.security

import com.projects.genericstructure.core.domain.UserRepository
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("Username not found")
        }

        val user = runBlocking { userRepository.findByEmail(username) }
        if (user == null) {
            throw UsernameNotFoundException("Username not found")
        }

        return User
            .withUsername(username)
            .password(user.password)
            .disabled(!user.enabled)
            .roles(*user.roles.toTypedArray())
            .build()
    }
}
