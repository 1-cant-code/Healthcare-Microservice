package com.pm.labservice.controller;

import java.util.List;
import java.util.UUID;

import com.pm.labservice.dto.LabTestCompletionRequest;
import com.pm.labservice.dto.LabTestDTO;
import com.pm.labservice.service.LabService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/labs")
@Validated
public class LabTestController {

	private final LabService labService;

	public LabTestController(LabService labService) {
		this.labService = labService;
	}
	@GetMapping
	@Operation(summary = "Get Laboratory tests")
	public ResponseEntity<List<LabTestDTO>> getLabTests() {
		List<LabTestDTO> appointments = labService.getLabs();
		return ResponseEntity.ok().body(appointments);
	}

	// Technician starts working on the lab test
	@PatchMapping("/{id}/start")
	public ResponseEntity<LabTestDTO> startLabTest(@PathVariable UUID id) {

		return ResponseEntity.ok(labService.startLabTest(id));
	}

	// Technician completes the lab test
	@PatchMapping("/{id}/complete")
	public ResponseEntity<LabTestDTO> completeLabTest(@PathVariable UUID id,
			@Valid @RequestBody LabTestCompletionRequest request) {

		return ResponseEntity.ok(labService.completeLabTest(id, request));
	}
}
