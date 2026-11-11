package com.pm.appointment.event

import java.time.LocalDateTime

data class LabTestRequested(
    val requestId: String,
    val patientId: String,
    val practitionerId: String,
    val testType: String?,
    val requestedAt: LocalDateTime
) {}
