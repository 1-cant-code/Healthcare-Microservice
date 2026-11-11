package com.pm.labservice.kafka;
import com.pm.labservice.dto.LabTestCompleted;
import com.pm.labservice.dto.LabTestFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LabTestProducerService {

  private static final Logger log = LoggerFactory.getLogger(LabTestProducerService.class);
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public LabTestProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendEvent(LabTestFailed labTestFailed) {
	  log.info("Lab test failed: {}", labTestFailed);
	  kafkaTemplate.send("lab-test-failed", labTestFailed.requestId(), labTestFailed);
  }

  public void sendEvent(LabTestCompleted labTestCompleted) {
	  log.info("Lab test completed: {}", labTestCompleted);
	  kafkaTemplate.send("lab-test-completed", labTestCompleted.requestId(), labTestCompleted);
  }
}
