package com.pm.labservice.dto;

public record LabTestFailed(
    String requestId,
    String patientId,
    String status,
    String resultReference
) {}
