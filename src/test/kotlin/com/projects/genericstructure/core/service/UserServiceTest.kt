package com.projects.genericstructure.core.service

import com.projects.genericstructure.adapters.security.JwtTokenProvider
import com.projects.genericstructure.core.domain.user.UserEmailAlreadyExistsException
import com.projects.genericstructure.core.domain.user.UserNotFoundException
import com.projects.genericstructure.core.domain.user.UserRepository
import com.projects.genericstructure.core.service.UserServiceTestFixture.USER_ID
import com.projects.genericstructure.core.service.UserServiceTestFixture.createUserCommand
import com.projects.genericstructure.core.service.UserServiceTestFixture.userCreated
import com.projects.genericstructure.core.service.UserServiceTestFixture.userDisabled
import com.projects.genericstructure.core.service.UserServiceTestFixture.userEnabled
import com.projects.genericstructure.core.service.user.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Optional

class UserServiceTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val tokenProvider = mockk<JwtTokenProvider>()
    val authenticationManager = mockk<ReactiveAuthenticationManager>()

    val createUserService = UserService(
        userRepository = userRepository,
        passwordEncoder = passwordEncoder,
        tokenProvider = tokenProvider,
        authenticationManager = authenticationManager,
    )

    afterTest {
        confirmVerified(userRepository, passwordEncoder, tokenProvider, authenticationManager)
    }

    describe("Create user") {

        it("Should throw exception because user already exists") {
            coEvery { userRepository.findByEmail(any()) } returns userCreated()

            val exception = shouldThrow<UserEmailAlreadyExistsException> {
                createUserService.create(createUserCommand())
            }

            exception.email shouldBe createUserCommand().email

            coVerify { userRepository.findByEmail(createUserCommand().email) }
        }

        it("Should create a user") {
            coEvery { userRepository.findByEmail(any()) } returns Optional.empty()
            coEvery { passwordEncoder.encode(any()) } returns "123"
            coEvery { userRepository.save(any()) } returns userCreated().get()

            createUserService.create(createUserCommand())

            coVerify { userRepository.findByEmail(createUserCommand().email) }
            coVerify { passwordEncoder.encode(createUserCommand().password) }
            coVerify { userRepository.save(userCreated().get()) }
        }
    }

    describe("Enable user") {
        it("Should throw exception because user does not exists") {
            coEvery { userRepository.findById(any()) } returns Optional.empty()

            val exception = shouldThrow<UserNotFoundException> {
                createUserService.enable(USER_ID)
            }

            exception.id shouldBe USER_ID

            coVerify { userRepository.findById(USER_ID) }
        }

        it("Should enable a user") {
            coEvery { userRepository.findById(any()) } returns userDisabled()
            coEvery { userRepository.save(any()) } returns userEnabled().get()

            createUserService.enable(USER_ID)

            coVerify { userRepository.findById(USER_ID) }
            coVerify { userRepository.save(userEnabled().get()) }
        }

        it("Should not do anything because user is already enabled") {
            coEvery { userRepository.findById(any()) } returns userEnabled()

            createUserService.enable(USER_ID)

            coVerify { userRepository.save(userEnabled().get()) }
            coVerify { userRepository.findById(USER_ID) }
        }
    }

    describe("Disable user") {
        it("Should throw exception because user does not exists") {
            coEvery { userRepository.findById(any()) } returns Optional.empty()

            val exception = shouldThrow<UserNotFoundException> {
                createUserService.disable(USER_ID)
            }

            exception.id shouldBe USER_ID

            coVerify { userRepository.findById(USER_ID) }
        }

        it("Should enable a user") {
            coEvery { userRepository.findById(any()) } returns userEnabled()
            coEvery { userRepository.save(any()) } returns userDisabled().get()

            createUserService.disable(USER_ID)

            coVerify { userRepository.findById(USER_ID) }
            coVerify { userRepository.save(userDisabled().get()) }
        }

        it("Should not do anything because user is already enabled") {
            coEvery { userRepository.findById(any()) } returns userDisabled()

            createUserService.disable(USER_ID)

            coVerify { userRepository.save(userDisabled().get()) }
            coVerify { userRepository.findById(USER_ID) }
        }
    }
})
