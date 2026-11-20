package com.pm.patientservice.exception

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {

        val errors: Map<String, String> =
            ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid message") }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailAlreadyExistsException(ex: EmailAlreadyExistsException): ResponseEntity<Map<String, String>> {

        log.warn("Email address already exist {}", ex.message);
        val errors: Map<String, String> = mapOf("message" to "Email address already exists")

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException::class)
    fun handlePatientNotFoundException(ex: PatientNotFoundException): ResponseEntity<Map<String, String>> {
        log.warn("Patient not found {}", ex.message);

        val errors: Map<String, String> = mapOf("message" to "Patient not found")
        return ResponseEntity.badRequest().body(errors);
    }
}
