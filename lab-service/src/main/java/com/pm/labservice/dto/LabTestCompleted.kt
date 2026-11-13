package com.pm.labservice.dto;

public record LabTestCompleted(
		String requestId,
		String patientId,
		String practitionerId,
		String status,
		String resultReference
) { }
