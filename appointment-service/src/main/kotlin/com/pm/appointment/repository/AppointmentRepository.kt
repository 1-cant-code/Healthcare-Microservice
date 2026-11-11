package com.pm.appointment.repository

import java.time.LocalDateTime
import java.util.UUID
import com.pm.appointment.model.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppointmentRepository : JpaRepository<Appointment, UUID> {

    fun existsByPractitionerIdAndStartTimeLessThanAndEndTimeGreaterThan(
        practitionerId: UUID,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): Boolean;
}
