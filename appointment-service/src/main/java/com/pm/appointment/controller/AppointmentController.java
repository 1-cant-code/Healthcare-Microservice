package com.pm.appointment.controller;

import java.util.List;
import java.util.UUID;

import com.pm.appointment.dto.AppointmentDTO;
import com.pm.appointment.dto.validators.CreateAppointmentValidationGroup;
import com.pm.appointment.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointment", description = "API for managing Appointments")
public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @GetMapping
  @Operation(summary = "Get Appointments")
  public ResponseEntity<List<AppointmentDTO>> getAppointments() {
    List<AppointmentDTO> appointments = appointmentService.getAppointments();
    return ResponseEntity.ok().body(appointments);
  }

  @PostMapping
  @Operation(summary = "Create a new Appointment")
  public ResponseEntity<AppointmentDTO> createAppointment(
      @Validated({Default.class, CreateAppointmentValidationGroup.class})
      @RequestBody AppointmentDTO appointmentRequestDTO) {

    AppointmentDTO appointmentDTO = appointmentService.createAppointment(
		    appointmentRequestDTO);

    return ResponseEntity.ok().body(appointmentDTO);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a new Appointment")
  public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable UUID id,
      @Validated({Default.class}) @RequestBody AppointmentDTO appointmentRequestDTO) {

    AppointmentDTO appointmentDTO = appointmentService.updateAppointment(id,
		    appointmentRequestDTO);

    return ResponseEntity.ok().body(appointmentDTO);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Appointment")
  public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
    appointmentService.deleteAppointment(id);
    return ResponseEntity.noContent().build();
  }
}
