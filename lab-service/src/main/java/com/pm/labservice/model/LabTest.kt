package com.pm.labservice.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "lab_tests")
public class LabTest {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String requestId;


	@Column(nullable = false)
	private String patientId;

	@Column(nullable = false)
	private String practitionerId;

	@Column(nullable = false)
	private String testType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LabTestStatus status;

	@Column
	private String result;

	@Column
	private String resultReference;

	@Column(nullable = false)
	private LocalDateTime requestedAt;

	private LocalDateTime completedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public LabTestStatus getStatus() {
		return status;
	}

	public void setStatus(LabTestStatus status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LocalDateTime getRequestedAt() {
		return requestedAt;
	}

	public void setRequestedAt(LocalDateTime requestedAt) {
		this.requestedAt = requestedAt;
	}

	public LocalDateTime getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}

	public String getResultReference() {
		return resultReference;
	}

	public void setResultReference(String resultReference) {
		this.resultReference = resultReference;
	}

	public String getPractitionerId() {
		return practitionerId;
	}

	public void setPractitionerId(String practitionerId) {
		this.practitionerId = practitionerId;
	}
	// Getters and setters
}
