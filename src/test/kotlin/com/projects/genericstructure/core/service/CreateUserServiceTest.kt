package com.projects.genericstructure.core.service

import com.projects.genericstructure.core.domain.user.UserEmailAlreadyExistsException
import com.projects.genericstructure.core.domain.user.UserNotFoundException
import com.projects.genericstructure.core.domain.user.UserRepository
import com.projects.genericstructure.core.service.CreateUserServiceTestFixture.USER_ID
import com.projects.genericstructure.core.service.CreateUserServiceTestFixture.createUserCommand
import com.projects.genericstructure.core.service.CreateUserServiceTestFixture.userCreated
import com.projects.genericstructure.core.service.CreateUserServiceTestFixture.userDisabled
import com.projects.genericstructure.core.service.CreateUserServiceTestFixture.userEnabled
import com.projects.genericstructure.core.service.user.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder

class CreateUserServiceTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()

    val createUserService = UserService(
        userRepository = userRepository,
        passwordEncoder = passwordEncoder
    )

    afterTest {
        confirmVerified(userRepository, passwordEncoder)
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
            coEvery { userRepository.findByEmail(any()) } returns null
            coEvery { passwordEncoder.encode(any()) } returns "123"
            coJustRun { userRepository.create(any()) }

            createUserService.create(createUserCommand())

            coVerify { userRepository.findByEmail(createUserCommand().email) }
            coVerify { passwordEncoder.encode(createUserCommand().password) }
            coVerify { userRepository.create(userCreated()) }
        }
    }

    describe("Enable user") {
        it("Should throw exception because user does not exists") {
            coEvery { userRepository.findById(any()) } returns null

            val exception = shouldThrow<UserNotFoundException> {
                createUserService.enable(USER_ID)
            }

            exception.id shouldBe USER_ID

            coVerify { userRepository.findById(USER_ID) }
        }

        it("Should enable a user") {
            coEvery { userRepository.findById(any()) } returns userDisabled()
            coJustRun { userRepository.update(any(), any()) }

            createUserService.enable(USER_ID)

            coVerify { userRepository.findById(USER_ID) }
            coVerify { userRepository.update(USER_ID, userEnabled()) }
        }

        it("Should not do anything because user is already enabled") {
            coEvery { userRepository.findById(any()) } returns userEnabled()

            createUserService.enable(USER_ID)

            coVerify { userRepository.findById(USER_ID) }
        }
    }

    describe("Disable user") {
        it("Should throw exception because user does not exists") {
            coEvery { userRepository.findById(any()) } returns null

            val exception = shouldThrow<UserNotFoundException> {
                createUserService.disable(USER_ID)
            }

            exception.id shouldBe USER_ID

            coVerify { userRepository.findById(USER_ID) }
        }

        it("Should enable a user") {
            coEvery { userRepository.findById(any()) } returns userEnabled()
            coJustRun { userRepository.update(any(), any()) }

            createUserService.disable(USER_ID)

            coVerify { userRepository.findById(USER_ID) }
            coVerify { userRepository.update(USER_ID, userDisabled()) }
        }

        it("Should not do anything because user is already enabled") {
            coEvery { userRepository.findById(any()) } returns userDisabled()

            createUserService.disable(USER_ID)

            coVerify { userRepository.findById(USER_ID) }
        }
    }
})
