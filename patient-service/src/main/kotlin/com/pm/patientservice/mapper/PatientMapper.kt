package com.pm.patientservice.mapper

import com.pm.patientservice.dto.PatientRequestDTO
import com.pm.patientservice.dto.PatientResponseDTO
import com.pm.patientservice.model.Patient
import java.time.LocalDate

object PatientMapper {

    fun toDTO(patient: Patient): PatientResponseDTO {
        return PatientResponseDTO(
            patient.id.toString(),
            patient.name,
            patient.email,
            patient.address,
            patient.dateOfBirth.toString()
        );
    }

    fun toEntity(patientRequestDTO: PatientRequestDTO): Patient {
        return Patient().apply {
            name = patientRequestDTO.name
            address = patientRequestDTO.address
            email = patientRequestDTO.email
            dateOfBirth = LocalDate.parse(patientRequestDTO.dateOfBirth)
            registeredDate = LocalDate.parse(patientRequestDTO.registeredDate)
        }
    }
}
