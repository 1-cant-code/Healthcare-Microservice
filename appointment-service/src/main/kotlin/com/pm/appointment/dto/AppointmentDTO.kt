package com.pm.appointment.dto;

import java.time.LocalDateTime
import java.util.UUID

import com.pm.appointment.dto.validators.CreateAppointmentValidationGroup

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AppointmentDTO(

    val id: UUID?,

    @field:NotNull(groups = [CreateAppointmentValidationGroup::class])
    val patientId: UUID,

    @field:NotNull(groups = [CreateAppointmentValidationGroup::class])
    val practitionerId: UUID,

    @field:NotNull(groups = [CreateAppointmentValidationGroup::class])
    val startTime: LocalDateTime,

    @field:NotNull(groups = [CreateAppointmentValidationGroup::class])
    val endTime: LocalDateTime,

    val status: AppointmentStatus?,

    @field:NotNull(groups = [CreateAppointmentValidationGroup::class])
    @field:Size(max = 500)
    val reason: String,

    @field:Size(max = 2000)
    val notes: String?,

    val testType: String?,

    val createdAt: LocalDateTime?,

    val updatedAt: LocalDateTime?

) {

}
