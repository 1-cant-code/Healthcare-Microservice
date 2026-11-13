package com.pm.labservice.mapper

import com.pm.labservice.dto.LabTestDTO
import com.pm.labservice.model.LabTest
import com.pm.labservice.event.LabTestRequested
import com.pm.labservice.model.LabTestStatus

object LabMapper {

    fun toDTO(labTest: LabTest): LabTestDTO {
        return LabTestDTO(
            labTest.id.toString(),
            labTest.requestId,
            labTest.patientId,
            labTest.practitionerId,
            labTest.testType,
            labTest.status.name,
            labTest.result,
            labTest.resultReference,
            labTest.requestedAt.toString(),
            labTest.completedAt?.let { it.toString() }
        );
    }

    fun toEntity(request: LabTestRequested): LabTest {
        val labTest: LabTest = LabTest().apply{
            requestId = request.requestId
            patientId = request.patientId
            practitionerId = request.practitionerId
            testType = request.testType
            requestedAt = request.requestedAt
        };

        return labTest;
    }

}
