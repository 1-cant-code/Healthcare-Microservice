package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import pm.events.PatientEvent

@Service
class KafkaProducerService(private val kafkaTemplate: KafkaTemplate<String, PatientEvent>) {

    private val log = LoggerFactory.getLogger(javaClass);

    fun sendEvent(patient: Patient) {
        val event = PatientEvent.newBuilder()
            .setPatientId(patient.id.toString())
            .setName(patient.name)
            .setEmail(patient.email)
            .setEventType("PATIENT_CREATED")
            .build()
        try {
            kafkaTemplate.send("patient", event);
        } catch (e: Exception) {
            log.error("Error sending PatientCreated event: {}", event);
        }
    }
}
