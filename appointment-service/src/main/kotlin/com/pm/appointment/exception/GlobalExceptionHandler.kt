package com.pm.appointment.exception

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

        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(AppointmentConflictException::class)
    fun handleAppointmentConflictException(ex: AppointmentConflictException): ResponseEntity<Map<String, String>> {

        log.warn("Appointment conflict: {}", ex.message)

        val errors = mapOf("message" to "Appointment conflict")

        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(AppointmentNotFoundException::class)
    fun handleAppointmentNotFoundException(ex: AppointmentNotFoundException): ResponseEntity<Map<String, String>> {

        log.warn("Appointment not found: {}", ex.message)

        val errors = mapOf("message" to "Appointment not found")

        return ResponseEntity.badRequest().body(errors)
    }
}
