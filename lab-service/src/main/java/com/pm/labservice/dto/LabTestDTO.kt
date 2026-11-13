package com.pm.labservice.dto;

public record LabTestDTO(
		String id,
		String requestId,
		String patientId,
		String practitionerId,
		String testType,
		String status,
		//lab test result for example a text value or brief conclusion
		String result,
		//a link to the full document containing the results, for example PDF
		String resultReference,
		String requestedAt,
		String completedAt
) {}
