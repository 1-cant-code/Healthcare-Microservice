package com.pm.appointment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.pm.appointment.dto.validators.CreateAppointmentValidationGroup;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AppointmentDTO(

		UUID id,

		@NotNull(groups = CreateAppointmentValidationGroup.class)
		UUID patientId,

		@NotNull(groups = CreateAppointmentValidationGroup.class)
		UUID practitionerId,

		@NotNull(groups = CreateAppointmentValidationGroup.class)
		LocalDateTime startTime,

		@NotNull(groups = CreateAppointmentValidationGroup.class)
		LocalDateTime endTime,

		AppointmentStatus status,

		@NotNull(groups = CreateAppointmentValidationGroup.class)
		@Size(max = 500)
		String reason,

		@Size(max = 2000)
		String notes,

		String testType,

		LocalDateTime createdAt,

		LocalDateTime updatedAt

) {

}
