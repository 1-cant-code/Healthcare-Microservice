
package com.pm.appointment.controller

import com.pm.appointment.dto.AppointmentDTO
import com.pm.appointment.dto.validators.CreateAppointmentValidationGroup
import com.pm.appointment.service.AppointmentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.groups.Default
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointment", description = "API for managing Appointments")
class AppointmentController(
    private val appointmentService: AppointmentService
) {

    @GetMapping
    @Operation(summary = "Get Appointments")
    fun getAppointments(): ResponseEntity<List<AppointmentDTO>> {
        val appointments = appointmentService.getAppointments()
        return ResponseEntity.ok(appointments)
    }

    @PostMapping
    @Operation(summary = "Create a new Appointment")
    fun createAppointment(
        @Validated(Default::class, CreateAppointmentValidationGroup::class)
        @RequestBody appointmentRequestDTO: AppointmentDTO
    ): ResponseEntity<AppointmentDTO> {

        val appointmentDTO =
            appointmentService.createAppointment(appointmentRequestDTO)

        return ResponseEntity.ok(appointmentDTO)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an Appointment")
    fun updateAppointment(
        @PathVariable id: UUID,
        @Validated(Default::class)
        @RequestBody appointmentRequestDTO: AppointmentDTO
    ): ResponseEntity<AppointmentDTO> {

        val appointmentDTO =
            appointmentService.updateAppointment(id, appointmentRequestDTO)

        return ResponseEntity.ok(appointmentDTO)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Appointment")
    fun deleteAppointment(@PathVariable id: UUID): ResponseEntity<Void> {
        appointmentService.deleteAppointment(id)
        return ResponseEntity.noContent().build()
    }
}
