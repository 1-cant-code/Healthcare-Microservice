package com.pm.labservice.service;

import com.pm.labservice.dto.LabTestCompleted
import com.pm.labservice.dto.LabTestCompletionRequest
import com.pm.labservice.dto.LabTestDTO
import com.pm.labservice.dto.LabTestFailed
import com.pm.labservice.event.LabTestRequested
import com.pm.labservice.exception.LabNotFoundException
import com.pm.labservice.kafka.LabTestProducerService
import com.pm.labservice.mapper.LabMapper
import com.pm.labservice.model.LabTest
import com.pm.labservice.model.LabTestStatus
import com.pm.labservice.repository.LabRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.LocalDateTime
import java.util.*

@Service
public class LabService(val labRepository: LabRepository, val labTestProducerService: LabTestProducerService) {

    @Transactional(readOnly = true)
    fun getLabs(): List<LabTestDTO> {
        return labRepository.findAll()
            .stream()
            .map(LabMapper::toDTO)
            .toList()
    }

    @KafkaListener(topics = ["lab.requests"], groupId = "lab-service")
    public fun handleLabRequest(request: LabTestRequested) {

        if (labRepository.existsByRequestId(request.requestId)) {
            labTestProducerService.sendEvent(
                LabTestFailed(
                    request.requestId,
                    request.patientId,
                    "FAILED",
                    null
                )
            )
            return
        }
        val labTest: LabTest = LabMapper.toEntity(request)
        labTest.status = LabTestStatus.REQUESTED
        labRepository.save(labTest)
    }

    @Transactional
    public fun startLabTest(labTestId: UUID): LabTestDTO {

        val labTest: LabTest = labRepository.findById(labTestId)
            .orElseThrow({ LabNotFoundException("LabTest not found with ID: $labTestId") })

        check(labTest.status == LabTestStatus.REQUESTED) { "Only REQUESTED lab tests can be started" }

        labTest.status = LabTestStatus.IN_PROGRESS

        return LabMapper.toDTO(labRepository.save(labTest))
    }

    @Transactional
    fun completeLabTest(labTestId: UUID, request: LabTestCompletionRequest): LabTestDTO {

        val labTest: LabTest = labRepository.findById(labTestId)
            .orElseThrow { LabNotFoundException("LabTest not found with ID: $labTestId") }

        check(labTest.status == LabTestStatus.IN_PROGRESS) { "Only IN_PROGRESS lab tests can be completed" }

        labTest.result = request.result
        labTest.status = LabTestStatus.COMPLETED
        labTest.completedAt = LocalDateTime.now(Clock.systemUTC())

        val updatedLabTest: LabTest = labRepository.save(labTest)

        labTestProducerService.sendEvent(
            LabTestCompleted(
                requestId = updatedLabTest.requestId,
                patientId = updatedLabTest.patientId,
                practitionerId = updatedLabTest.practitionerId,
                status = LabTestStatus.COMPLETED.name,
                resultReference = updatedLabTest.resultReference
            )
        );

        return LabMapper.toDTO(updatedLabTest);
    }

    @Transactional
    fun deleteLabTest(id: UUID) {
        labRepository.deleteById(id);
    }
}
