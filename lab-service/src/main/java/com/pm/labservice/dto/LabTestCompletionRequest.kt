package com.pm.labservice.dto

import jakarta.validation.constraints.NotBlank

data class LabTestCompletionRequest(
    @field:NotBlank
    val result: String,
    val resultReference: String,
    @field:NotBlank
    val practitionerId: String
) {

}
