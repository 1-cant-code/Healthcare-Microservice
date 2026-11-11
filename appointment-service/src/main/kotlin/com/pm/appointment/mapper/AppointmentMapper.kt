
package com.pm.appointment.mapper

import com.pm.appointment.dto.AppointmentDTO
import com.pm.appointment.model.Appointment

object AppointmentMapper {

    fun toDTO(appointment: Appointment): AppointmentDTO {
        return AppointmentDTO(
            id = appointment.id,
            patientId = appointment.patientId,
            practitionerId = appointment.practitionerId,
            startTime = appointment.startTime,
            endTime = appointment.endTime,
            status = appointment.status,
            reason = appointment.reason,
            notes = appointment.notes,
            testType = appointment.testType,
            createdAt = appointment.createdAt,
            updatedAt = appointment.updatedAt
        )
    }

    fun toEntity(dto: AppointmentDTO): Appointment {
        return Appointment().apply {
            patientId = dto.patientId
            practitionerId = dto.practitionerId
            startTime = dto.startTime
            endTime = dto.endTime
            reason = dto.reason
            notes = dto.notes
            testType = dto.testType
        }
    }
}
