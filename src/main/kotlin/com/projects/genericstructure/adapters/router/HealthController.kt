package com.projects.genericstructure.adapters.router

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun health() {
    }
}
