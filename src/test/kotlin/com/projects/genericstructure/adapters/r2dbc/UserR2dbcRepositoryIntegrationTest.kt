package com.projects.genericstructure.adapters.r2dbc

import com.projects.genericstructure.adapters.r2dbc.DatabaseClientFixture.databaseClient
import com.projects.genericstructure.adapters.r2dbc.UserR2dbcRepositoryIntegrationTestFixture.USER_EMAIL
import com.projects.genericstructure.adapters.r2dbc.UserR2dbcRepositoryIntegrationTestFixture.USER_INVALID_EMAIL
import com.projects.genericstructure.adapters.r2dbc.UserR2dbcRepositoryIntegrationTestFixture.expectedUser
import com.projects.genericstructure.adapters.r2dbc.UserR2dbcRepositoryIntegrationTestFixture.sqlMockUser
import com.projects.genericstructure.adapters.r2dbc.UserR2dbcRepositoryIntegrationTestFixture.sqlUnmockUser
import com.projects.genericstructure.adapters.r2dbc.user.UserR2dbcRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.r2dbc.core.await

class UserR2dbcRepositoryIntegrationTest : DescribeSpec({

    val db = databaseClient()
    val userRepository = UserR2dbcRepository(db)

    beforeTest {
        db.sql(sqlUnmockUser()).await()
        db.sql(sqlMockUser()).await()
    }

    afterTest {
        db.sql(sqlUnmockUser()).await()
    }

    describe("Find user by email") {

        it("Should find user by email") {
            userRepository.findByEmail(USER_EMAIL) shouldBe expectedUser()
        }

        it("Should return null because user does not exist") {
            userRepository.findByEmail(USER_INVALID_EMAIL) shouldBe null
        }
    }
})
