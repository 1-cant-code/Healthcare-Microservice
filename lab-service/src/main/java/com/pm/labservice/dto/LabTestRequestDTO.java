package com.pm.labservice.dto;

import com.pm.labservice.dto.validators.CreateLabValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LabTestRequestDTO(
		@NotBlank(message = "Name is required")
		@Size(max = 100, message = "Name cannot exceed 100 characters")
		String name,

		@NotBlank(message = "Email is required1")
		@Email(message = "Email should be valid")
		String email,

		@NotBlank(message = "Address is required")
		String address,

		@NotBlank(message = "Date of birth is required")
		String dateOfBirth,

		@NotBlank(groups = CreateLabValidationGroup.class, message =
				"Registered date is required")
		String registeredDate) {

}
