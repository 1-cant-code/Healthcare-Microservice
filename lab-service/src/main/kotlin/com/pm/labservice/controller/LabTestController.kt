package com.pm.labservice.controller

import java.util.UUID

import com.pm.labservice.dto.LabTestCompletionRequest
import com.pm.labservice.dto.LabTestDTO
import com.pm.labservice.service.LabService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/labs")
@Validated
public class LabTestController(val labService: LabService) {

    @GetMapping
    @Operation(summary = "Get Laboratory tests")
    fun getLabTests(): ResponseEntity<List<LabTestDTO>> {
        val appointments: List<LabTestDTO> = labService.getLabs();
        return ResponseEntity.ok().body(appointments);
    }

    @PatchMapping("/{id}/start")
    @Operation(summary = " Technician starts working on the lab test")
    fun publicstartLabTest(@PathVariable id: UUID): ResponseEntity<LabTestDTO> {

        return ResponseEntity.ok(labService.startLabTest(id));
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Technician completes the lab test")
    fun completeLabTest(
        @PathVariable id: UUID,
        @Valid @RequestBody request: LabTestCompletionRequest
    ): ResponseEntity<LabTestDTO> {
        return ResponseEntity.ok(labService.completeLabTest(id, request));
    }
}
