package com.pm.patientservice.dto

data class PatientResponseDTO(
    val id: String,
    val name: String,
    val email: String,
    val address: String,
    val dateOfBirth: String
) {

}
