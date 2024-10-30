package com.projects.genericstructure.adapters.router

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleIllegalArgumentException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(ex.message ?: "Something went wrong, please try later!")
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}

data class ErrorResponseBody(
    val error: String
)
