package com.projects.genericstructure

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.core.env.Environment
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
class GenericStructureApplicationTest(
    private val env: Environment,
    private val webClient: WebTestClient
) : DescribeSpec({

    describe("Test root path") {
        it("Should return 401") {
            webClient
                .get()
                .uri("")
                .exchange()
                .expectStatus().isUnauthorized
                .expectBody().isEmpty
        }
    }

    describe("Test health check") {
        it("Should be healthy") {
            webClient
                .get()
                .uri("http://localhost:${env.getProperty("local.management.port")}/actuator/health")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("status").isEqualTo("UP")
        }
    }
})
