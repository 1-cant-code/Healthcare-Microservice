package com.pm.appointment.kafka

import com.pm.appointment.event.LabTestRequested
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class AppointmentProducerService (val kafkaTemplate: KafkaTemplate<String, LabTestRequested> ){

  val  log: Logger = LoggerFactory.getLogger(javaClass);

  fun sendEvent(labTestRequested: LabTestRequested ) {
	  log.info("Lab test requested: {}", labTestRequested);
	  kafkaTemplate.send(
			  "lab.requests",
			  labTestRequested.requestId,
			  labTestRequested
	  );
  }
}
