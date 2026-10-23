package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;
import java.time.LocalDate;

public class PatientMapper {
  private PatientMapper() {
    /* This utility class should not be instantiated */
  }

  public static PatientResponseDTO toDTO(Patient patient) {
	  return new PatientResponseDTO(patient.getId().toString(), patient.getName(), patient.getEmail(),
		    patient.getAddress(), patient.getDateOfBirth().toString());
  }

  public static Patient toEntity(PatientRequestDTO patientRequestDTO) {
    Patient patient = new Patient();
    patient.setName(patientRequestDTO.name());
    patient.setAddress(patientRequestDTO.address());
    patient.setEmail(patientRequestDTO.email());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.dateOfBirth()));
    patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.registeredDate()));
    return patient;
  }
}
