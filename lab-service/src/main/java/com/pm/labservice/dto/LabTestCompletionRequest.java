package com.pm.labservice.dto;

import jakarta.validation.constraints.NotBlank;

public record LabTestCompletionRequest(
		@NotBlank
		String result,

		String resultReference,

		@NotBlank
		String practitionerId
) {

}
