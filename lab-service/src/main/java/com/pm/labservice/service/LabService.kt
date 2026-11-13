package com.pm.labservice.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.pm.labservice.dto.LabTestCompleted;
import com.pm.labservice.dto.LabTestCompletionRequest;
import com.pm.labservice.dto.LabTestFailed;
import com.pm.labservice.dto.LabTestDTO;
import com.pm.labservice.event.LabTestRequested;
import com.pm.labservice.exception.LabNotFoundException;
import com.pm.labservice.kafka.LabTestProducerService;
import com.pm.labservice.mapper.LabMapper;
import com.pm.labservice.model.LabTest;
import com.pm.labservice.model.LabTestStatus;
import com.pm.labservice.repository.LabRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LabService {

	private final LabRepository labRepository;

	private final LabTestProducerService labTestProducerService;

	public LabService(LabRepository labRepository, LabTestProducerService labTestProducerService) {
		this.labRepository = labRepository;
		this.labTestProducerService = labTestProducerService;
	}

	@Transactional(readOnly = true)
	public List<LabTestDTO> getLabs() {
		return labRepository.findAll()
				.stream()
				.map(LabMapper::toDTO)
				.toList();
	}

	@KafkaListener(topics = "lab.requests", groupId = "lab-service")
	public void handleLabRequest(LabTestRequested request) {

		if (labRepository.existsByRequestId(request.requestId())) {
			labTestProducerService.sendEvent(
					new LabTestFailed(request.requestId(), request.patientId(), "FAILED", null)
			);
			return;
		}
		LabTest labTest = LabMapper.toEntity(request);
		labTest.setStatus(LabTestStatus.REQUESTED);
		labRepository.save(labTest);
	}

	@Transactional
	public LabTestDTO startLabTest(UUID labTestId) {

		LabTest labTest = labRepository.findById(labTestId)
				.orElseThrow(() -> new LabNotFoundException("LabTest not found with ID: " + labTestId));

		if (labTest.getStatus() != LabTestStatus.REQUESTED) {
			throw new IllegalStateException("Only REQUESTED lab tests can be started");
		}

		labTest.setStatus(LabTestStatus.IN_PROGRESS);

		return LabMapper.toDTO(labRepository.save(labTest));
	}

	@Transactional
	public LabTestDTO completeLabTest(UUID labTestId, LabTestCompletionRequest request) {

		LabTest labTest = labRepository.findById(labTestId)
				.orElseThrow(() -> new LabNotFoundException("LabTest not found with ID: " + labTestId));

		if (labTest.getStatus() != LabTestStatus.IN_PROGRESS) {
			throw new IllegalStateException("Only IN_PROGRESS lab tests can be completed");
		}

		labTest.setResult(request.result());
		labTest.setStatus(LabTestStatus.COMPLETED);
		labTest.setCompletedAt(LocalDateTime.now(Clock.systemUTC()));

		LabTest updatedLabTest = labRepository.save(labTest);

		labTestProducerService.sendEvent(
				new LabTestCompleted(
						updatedLabTest.getRequestId(),
						updatedLabTest.getPatientId(),
						updatedLabTest.getPractitionerId(),
						LabTestStatus.COMPLETED.name(),
						updatedLabTest.getResultReference()
				)
		);

		return LabMapper.toDTO(updatedLabTest);
	}

	@Transactional
	public void deleteLabTest(UUID id) {
		labRepository.deleteById(id);
	}
}
