package com.pm.patientservice.repository

import com.pm.patientservice.model.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PatientRepository : JpaRepository<Patient, UUID> {
    fun existsByEmail(email: String): Boolean;
    fun existsByEmailAndIdNot(email: String, id: UUID): Boolean;
}
