package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

data class PatientRequestDTO(
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 100, message = "Name cannot exceed 100 characters")
    var name: String,

    @field:NotBlank(message = "Email is required1")
    @field:Email(message = "Email should be valid")
    var email: String,

    @field:NotBlank(message = "Address is required")
    var address: String,

    @field:NotBlank(message = "Date of birth is required")
    var dateOfBirth: String,

    @field:NotBlank(
        groups = [CreatePatientValidationGroup::class], message =
            "Registered date is required"
    )
    var registeredDate: String
) {

}
