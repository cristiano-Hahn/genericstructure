package com.projects.genericstructure.adapters.security

import com.projects.genericstructure.adapters.security.CustomUserDetailsServiceTestFixture.USER_EMAIL
import com.projects.genericstructure.adapters.security.CustomUserDetailsServiceTestFixture.expectedUserDetailsUser
import com.projects.genericstructure.adapters.security.CustomUserDetailsServiceTestFixture.user
import com.projects.genericstructure.core.domain.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomUserDetailsServiceTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val customUserDetailsService = CustomUserDetailsService(userRepository)

    afterTest {
        confirmVerified(userRepository)
    }

    describe("Load user by username") {

        it("Should throw exception because username is null") {
            shouldThrow<UsernameNotFoundException> {
                customUserDetailsService.loadUserByUsername(null)
            }
        }

        it("Should throw exception because username was not found") {
            coEvery { userRepository.findByEmail(any()) } returns null

            shouldThrow<UsernameNotFoundException> {
                customUserDetailsService.loadUserByUsername(USER_EMAIL)
            }

            coVerify { userRepository.findByEmail(USER_EMAIL) }
        }

        it("Should find and return user") {
            coEvery { userRepository.findByEmail(any()) } returns user()

            val userDetailsUser = customUserDetailsService.loadUserByUsername(USER_EMAIL)
            userDetailsUser shouldBe expectedUserDetailsUser()

            coVerify { userRepository.findByEmail(USER_EMAIL) }
        }
    }
})
