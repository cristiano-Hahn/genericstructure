package com.projects.genericstructure.adapters.router

import com.projects.genericstructure.adapters.router.request.CreateUserRequest
import com.projects.genericstructure.adapters.router.request.CreateUserResponse
import com.projects.genericstructure.adapters.router.request.toCommand
import com.projects.genericstructure.adapters.router.request.toCreateUserResponse
import com.projects.genericstructure.core.service.CreateUserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController()
@RequestMapping("/users")
class UserController(
    private val createUserService: CreateUserService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    suspend fun createUser(@RequestBody request: CreateUserRequest): CreateUserResponse {
        val userId = UUID.randomUUID()
        val user = createUserService.execute(request.toCommand(userId))
        return user.toCreateUserResponse()
    }
}
