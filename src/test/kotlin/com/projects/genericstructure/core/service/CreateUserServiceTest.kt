package com.projects.genericstructure.core.service

import com.projects.genericstructure.core.domain.user.UserEmailAlreadyExistsException
import com.projects.genericstructure.core.domain.user.UserRepository
import com.projects.genericstructure.core.service.CreateUserServiceTestFixture.createUserCommand
import com.projects.genericstructure.core.service.CreateUserServiceTestFixture.user
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
            coEvery { userRepository.findByEmail(any()) } returns user()

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
            coVerify { userRepository.create(user()) }
        }
    }
})
