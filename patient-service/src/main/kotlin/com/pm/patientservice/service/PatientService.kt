package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO
import com.pm.patientservice.dto.PatientResponseDTO
import com.pm.patientservice.exception.EmailAlreadyExistsException
import com.pm.patientservice.exception.PatientNotFoundException
import com.pm.patientservice.grpc.BillingServiceGrpcClient
import com.pm.patientservice.kafka.KafkaProducerService
import com.pm.patientservice.mapper.PatientMapper
import com.pm.patientservice.model.Patient
import com.pm.patientservice.repository.PatientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
public class PatientService(
    val patientRepository: PatientRepository,
    val billingServiceGrpcClient: BillingServiceGrpcClient,
    val kafkaProducerService: KafkaProducerService
) {


    @Transactional(readOnly = true)
    fun getPatients(): List<PatientResponseDTO> {
        val patients: List<Patient> = patientRepository.findAll()

        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    fun createPatient(patientRequestDTO: PatientRequestDTO): PatientResponseDTO {
        if (patientRepository.existsByEmail(patientRequestDTO.email)) {
            throw EmailAlreadyExistsException(
                "A patient with this email " + "already exists"
                        + patientRequestDTO.email
            );
        }

        val newPatient: Patient = patientRepository.save(
            PatientMapper.toEntity(patientRequestDTO)
        );

        //    billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
        //        newPatient.getName(), newPatient.getEmail());

        kafkaProducerService.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    @Transactional
    fun updatePatient(id: UUID, patientRequestDTO: PatientRequestDTO): PatientResponseDTO {

        val patient: Patient = patientRepository.findById(id)
            .orElseThrow { PatientNotFoundException("Patient not found with ID: $id") }

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.email, id)) {
            throw EmailAlreadyExistsException("A patient with this email already exists ${patientRequestDTO.email}")
        }

        patient.name = patientRequestDTO.name
        patient.address = patientRequestDTO.address
        patient.email = patientRequestDTO.email
        patient.dateOfBirth = LocalDate.parse(patientRequestDTO.dateOfBirth)
        patient.registeredDate = LocalDate.parse(patientRequestDTO.registeredDate)

        val updatedPatient: Patient = patientRepository.save(patient)
        return PatientMapper.toDTO(updatedPatient);
    }

    @Transactional
    fun deletePatient(id: UUID) {
        patientRepository.deleteById(id);
    }
}
