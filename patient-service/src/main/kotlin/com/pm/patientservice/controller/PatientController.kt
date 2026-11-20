package com.pm.patientservice.controller

import com.pm.patientservice.dto.PatientRequestDTO
import com.pm.patientservice.dto.PatientResponseDTO
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup
import com.pm.patientservice.service.PatientService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.groups.Default
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
 class PatientController (val patientService: PatientService){

  @GetMapping
  @Operation(summary = "Get Patients")
   fun getPatients() : ResponseEntity<List<PatientResponseDTO>>{
    val  patients: List<PatientResponseDTO> = patientService.getPatients();
    return ResponseEntity.ok().body(patients);
  }

  @PostMapping
  @Operation(summary = "Create a new Patient")
  fun  createPatient(
      @Validated(Default::class, CreatePatientValidationGroup::class)
      @RequestBody  patientRequestDTO: PatientRequestDTO) : ResponseEntity<PatientResponseDTO> {

    val patientResponseDTO: PatientResponseDTO  = patientService.createPatient(patientRequestDTO);

    return ResponseEntity.ok().body(patientResponseDTO);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a new Patient")
  fun  updatePatient(@PathVariable id: UUID ,
      @Validated(Default::class) @RequestBody  patientRequestDTO: PatientRequestDTO) : ResponseEntity<PatientResponseDTO>{

    val  patientResponseDTO = patientService.updatePatient(id,
        patientRequestDTO);

    return ResponseEntity.ok().body(patientResponseDTO);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Patient")
  fun deletePatient(@PathVariable  id: UUID) : ResponseEntity<Void> {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
