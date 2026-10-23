package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducerService;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

	private final PatientRepository patientRepository;

	private final BillingServiceGrpcClient billingServiceGrpcClient;

	private final KafkaProducerService kafkaProducerService;

	public PatientService(PatientRepository patientRepository,
			BillingServiceGrpcClient billingServiceGrpcClient,
			KafkaProducerService kafkaProducerService) {
		this.patientRepository = patientRepository;
		this.billingServiceGrpcClient = billingServiceGrpcClient;
		this.kafkaProducerService = kafkaProducerService;
	}

	@Transactional(readOnly = true)
	public List<PatientResponseDTO> getPatients() {
		List<Patient> patients = patientRepository.findAll();

		return patients.stream().map(PatientMapper::toDTO).toList();
	}

	public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
		if (patientRepository.existsByEmail(patientRequestDTO.email())) {
			throw new EmailAlreadyExistsException(
					"A patient with this email " + "already exists"
							+ patientRequestDTO.email());
		}

		Patient newPatient = patientRepository.save(
				PatientMapper.toEntity(patientRequestDTO));

		//    billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
		//        newPatient.getName(), newPatient.getEmail());

		kafkaProducerService.sendEvent(newPatient);

		return PatientMapper.toDTO(newPatient);
	}

	@Transactional
	public PatientResponseDTO updatePatient(UUID id,
			PatientRequestDTO patientRequestDTO) {

		Patient patient = patientRepository.findById(id).orElseThrow(
				() -> new PatientNotFoundException("Patient not found with ID: " + id));

		if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.email(),
				id)) {
			throw new EmailAlreadyExistsException(
					"A patient with this email " + "already exists"
							+ patientRequestDTO.email());
		}

		patient.setName(patientRequestDTO.name());
		patient.setAddress(patientRequestDTO.address());
		patient.setEmail(patientRequestDTO.email());
		patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.dateOfBirth()));
		patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.registeredDate()));

		Patient updatedPatient = patientRepository.save(patient);
		return PatientMapper.toDTO(updatedPatient);
	}

	@Transactional
	public void deletePatient(UUID id) {
		patientRepository.deleteById(id);
	}
}
