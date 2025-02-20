package com.projects.genericstructure.adapters.router

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleIllegalArgumentException(ex: Exception): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(ex.message ?: "Something went wrong, please try later!")
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: Exception): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(ex.message ?: "Something went wrong, please try later!")
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }
}

data class ErrorResponseBody(
    val message: String
)
