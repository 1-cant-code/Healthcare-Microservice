package com.pm.labservice.kafka;

import com.pm.labservice.dto.LabTestCompleted;
import com.pm.labservice.dto.LabTestFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
class LabTestProducerService(private val kafkaTemplate: KafkaTemplate<String, Any>) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun sendEvent(labTestFailed: LabTestFailed) {
        log.info("Lab test failed: {}", labTestFailed)
        kafkaTemplate.send("lab-test-failed", labTestFailed.requestId, labTestFailed)
    }

    fun sendEvent(labTestCompleted: LabTestCompleted) {
        log.info("Lab test completed: {}", labTestCompleted)
        kafkaTemplate.send("lab-test-completed", labTestCompleted.requestId, labTestCompleted)
    }
}
