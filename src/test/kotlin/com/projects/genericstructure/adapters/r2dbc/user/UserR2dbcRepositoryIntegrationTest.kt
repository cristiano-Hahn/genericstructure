package com.projects.genericstructure.adapters.r2dbc.user

import com.projects.genericstructure.adapters.r2dbc.DatabaseClientFixture.databaseClient
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepositoryIntegrationTestFixture.USER_EMAIL
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepositoryIntegrationTestFixture.USER_ID
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepositoryIntegrationTestFixture.USER_INVALID_EMAIL
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepositoryIntegrationTestFixture.USER_INVALID_ID
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepositoryIntegrationTestFixture.expectedUser
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepositoryIntegrationTestFixture.sqlMockUser
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepositoryIntegrationTestFixture.sqlUnmockUser
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepositoryIntegrationTestFixture.userUpdated
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.r2dbc.core.await
import java.util.UUID

class UserR2dbcRepositoryIntegrationTest : DescribeSpec({

    val db = databaseClient()
    val userRepository = UserR2dbcRepository(db)

    beforeTest {
        db.sql(sqlUnmockUser()).await()
    }

    afterTest {
        db.sql(sqlUnmockUser()).await()
    }

    describe("Find user by email") {

        it("Should find user by email") {
            db.sql(sqlMockUser()).await()
            userRepository.findByEmail(USER_EMAIL) shouldBe expectedUser()
        }

        it("Should return null because user does not exist") {
            db.sql(sqlMockUser()).await()
            userRepository.findByEmail(USER_INVALID_EMAIL) shouldBe null
        }
    }

    describe("Find user by id") {

        it("Should find user by id") {
            db.sql(sqlMockUser()).await()
            userRepository.findById(UUID.fromString(USER_ID)) shouldBe expectedUser()
        }

        it("Should return null because user does not exist") {
            db.sql(sqlMockUser()).await()
            userRepository.findById(UUID.fromString(USER_INVALID_ID)) shouldBe null
        }
    }

    describe("Create user") {
        it("Should create a user") {
            userRepository.findByEmail(expectedUser().email) shouldBe null
            userRepository.create(expectedUser())
            userRepository.findByEmail(expectedUser().email) shouldBe expectedUser()
        }
    }

    describe("Update user") {
        it("Should update a user") {
            db.sql(sqlMockUser()).await()

            val userUpdated = userUpdated()
            userRepository.update(userUpdated.id, userUpdated)
            userRepository.findByEmail(userUpdated.email) shouldBe userUpdated
        }
    }
})
