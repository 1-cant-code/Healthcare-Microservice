package com.pm.labservice.dto

data class LabTestFailed(
    val requestId: String,
    val patientId: String,
    val status: String,
    val resultReference: String?
) {}
