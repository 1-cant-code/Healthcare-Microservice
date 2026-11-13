package com.pm.labservice.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors: Map<String, String> = ex.bindingResult.fieldErrors
            .associate { it.field to (it.defaultMessage ?: "Invalid message") }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailAlreadyExistsException(ex: EmailAlreadyExistsException): ResponseEntity<Map<String, String>> {

        log.warn("Email address already exist {}", ex.message);
        val errors: Map<String, String> = mapOf("message" to "Email address already exists")
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(LabNotFoundException::class)
    fun handleLabNotFoundException(ex: LabNotFoundException): ResponseEntity<Map<String, String>> {
        log.warn("LabTest not found {}", ex.message);

        val errors: Map<String, String> = mapOf("message" to "LabTest not found")
        return ResponseEntity.badRequest().body(errors);
    }
}
