package com.pm.labservice.dto

import com.pm.labservice.dto.validators.CreateLabValidationGroup
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LabTestRequestDTO(
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 100, message = "Name cannot exceed 100 characters")
    val name: String,

    @field:NotBlank(message = "Email is required1")
    @field:Email(message = "Email should be valid")
    val email: String,

    @field:NotBlank(message = "Address is required")
    val address: String,

    @field:NotBlank(message = "Date of birth is required")
    val dateOfBirth: String,

    @field:NotBlank(
        groups = [CreateLabValidationGroup::class], message =
            "Registered date is required"
    )
    val registeredDate: String
)
