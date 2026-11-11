
package com.pm.appointment.model

import com.pm.appointment.dto.AppointmentStatus
import jakarta.persistence.*
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "appointments")
class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false)
    lateinit var patientId: UUID

    @Column(nullable = false)
    lateinit var practitionerId: UUID

    @Column(nullable = false)
    lateinit var startTime: LocalDateTime

    @Column(nullable = false)
    lateinit var endTime: LocalDateTime

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var status: AppointmentStatus

    @Column
    var testType: String? = null

    @Column(nullable = false, length = 500)
    lateinit var reason: String

    @Column(length = 2000)
    var notes: String? = null

    @Column(nullable = false, updatable = false)
    lateinit var createdAt: LocalDateTime
        private set

    var updatedAt: LocalDateTime? = null
        private set

    @PrePersist
    protected fun onCreate() {
        createdAt = LocalDateTime.now(Clock.systemUTC())
        updatedAt = createdAt

        if (!::status.isInitialized) {
            status = AppointmentStatus.SCHEDULED
        }
    }

    @PreUpdate
    protected fun onUpdate() {
        updatedAt = LocalDateTime.now(Clock.systemUTC())
    }
}
