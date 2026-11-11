package com.pm.appointment.mapper;

import com.pm.appointment.dto.AppointmentDTO;
import com.pm.appointment.model.Appointment;

public class AppointmentMapper {
  private AppointmentMapper() {
    /* This utility class should not be instantiated */
  }

	public static AppointmentDTO toDTO(Appointment appointment) {
		return new AppointmentDTO(
				appointment.getId(),
				appointment.getPatientId(),
				appointment.getPractitionerId(),
				appointment.getStartTime(),
				appointment.getEndTime(),
				appointment.getStatus(),
				appointment.getReason(),
				appointment.getNotes(),
				appointment.getTestType(),
				appointment.getCreatedAt(),
				appointment.getUpdatedAt()
		);
	}

	public static Appointment toEntity(AppointmentDTO dto) {
		Appointment appointment = new Appointment();

		appointment.setPatientId(dto.patientId());
		appointment.setPractitionerId(dto.practitionerId());
		appointment.setStartTime(dto.startTime());
		appointment.setEndTime(dto.endTime());
		appointment.setReason(dto.reason());
		appointment.setNotes(dto.notes());
		appointment.setTestType(dto.testType());

		return appointment;
	}
}
