package com.pm.labservice.dto

data class LabTestCompleted(
    val requestId: String,
    val patientId: String,
    val practitionerId: String,
    val status: String,
    val resultReference: String?
) {}
