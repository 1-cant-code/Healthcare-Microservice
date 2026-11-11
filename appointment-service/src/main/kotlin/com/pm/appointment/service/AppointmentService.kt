package com.pm.appointment.service

import com.pm.appointment.dto.AppointmentDTO
import com.pm.appointment.event.LabTestRequested
import com.pm.appointment.exception.AppointmentConflictException
import com.pm.appointment.kafka.AppointmentProducerService
import com.pm.appointment.mapper.AppointmentMapper
import com.pm.appointment.repository.AppointmentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID

@Service
class AppointmentService(
    private val appointmentRepository: AppointmentRepository,
    private val appointmentProducerService: AppointmentProducerService,
) {

    @Transactional(readOnly = true)
    fun getAppointments(): List<AppointmentDTO> {
        return appointmentRepository.findAll()
            .map { AppointmentMapper.toDTO(it) }
    }

    @Transactional
    fun createAppointment(appointmentDTO: AppointmentDTO): AppointmentDTO {

        if (appointmentRepository
                .existsByPractitionerIdAndStartTimeLessThanAndEndTimeGreaterThan(
                    appointmentDTO.practitionerId,
                    appointmentDTO.endTime,
                    appointmentDTO.startTime
                )
        ) {
            throw AppointmentConflictException("Practitioner already has an appointment at this time")
        }

        val appointment = appointmentRepository.save(AppointmentMapper.toEntity(appointmentDTO))

        appointmentProducerService.sendEvent(
            LabTestRequested(
                UUID.randomUUID().toString(),
                appointmentDTO.patientId.toString(),
                appointmentDTO.practitionerId.toString(),
                appointmentDTO.testType,
                LocalDateTime.now(Clock.systemUTC())
            )
        )

        return AppointmentMapper.toDTO(appointment)
    }

    @Transactional
    fun deleteAppointment(id: UUID) {
        appointmentRepository.deleteById(id)
    }

    fun updateAppointment(
        id: UUID,
        appointmentRequestDTO: AppointmentDTO
    ): AppointmentDTO {
        throw UnsupportedOperationException()
    }
}
