package com.pm.labservice.dto

data class LabTestDTO(
    val id: String,
    val requestId: String,
    val patientId: String,
    val practitionerId: String,
    val testType: String,
    val status: String,
    //lab test result for example a text value or brief conclusion
    val result: String?,
    //a link to the full document containing the results, for example PDF
    val resultReference: String?,
    val requestedAt: String,
    val completedAt: String?
) {}
