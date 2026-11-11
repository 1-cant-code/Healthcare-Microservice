package com.pm.labservice.event;

import java.time.LocalDateTime;

public record LabTestRequested(
    String requestId,
    String patientId,
    String practitionerId,
    String testType,
    LocalDateTime requestedAt
) {}
