package com.pm.labservice.mapper;

import com.pm.labservice.dto.LabTestDTO;
import com.pm.labservice.model.LabTest;
import com.pm.labservice.event.LabTestRequested;
import com.pm.labservice.model.LabTestStatus;

public class LabMapper {

	private LabMapper() {
		/* This utility class should not be instantiated */
	}

	public static LabTestDTO toDTO(LabTest labTest) {
		return new LabTestDTO(
				labTest.getId().toString(),
				labTest.getRequestId(),
				labTest.getPatientId(),
				labTest.getPractitionerId(),
				labTest.getTestType(),
				labTest.getStatus().name(),
				labTest.getResult(),
				labTest.getResultReference(),
				labTest.getRequestedAt().toString(),
				labTest.getCompletedAt() != null
						? labTest.getCompletedAt().toString()
						: null
		);
	}

	public static LabTest toEntity(LabTestRequested request) {
		LabTest labTest = new LabTest();

		labTest.setRequestId(request.requestId());
		labTest.setPatientId(request.patientId());
		labTest.setPractitionerId(request.practitionerId());
		labTest.setTestType(request.testType());
		labTest.setRequestedAt(request.requestedAt());
		labTest.setStatus(LabTestStatus.COMPLETED);

		return labTest;
	}

}
