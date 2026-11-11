package com.pm.appointment.kafka;

import com.pm.appointment.event.LabTestRequested;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProducerService {

  private static final Logger log = LoggerFactory.getLogger(AppointmentProducerService.class);
  private final KafkaTemplate<String, LabTestRequested> kafkaTemplate;

  public AppointmentProducerService(KafkaTemplate<String, LabTestRequested> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendEvent(LabTestRequested labTestRequested) {
	  log.info("Lab test requested: {}", labTestRequested);
	  kafkaTemplate.send(
			  "lab.requests",
			  labTestRequested.requestId(),
			  labTestRequested
	  );
  }
}
