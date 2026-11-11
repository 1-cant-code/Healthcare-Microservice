package com.pm.appointment.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.pm.appointment.dto.AppointmentDTO;
import com.pm.appointment.event.LabTestRequested;
import com.pm.appointment.exception.AppointmentConflictException;
import com.pm.appointment.kafka.AppointmentProducerService;
import com.pm.appointment.mapper.AppointmentMapper;
import com.pm.appointment.model.Appointment;
import com.pm.appointment.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;

	private final AppointmentProducerService appointmentProducerService;

	public AppointmentService(AppointmentRepository appointmentRepository,
			AppointmentProducerService appointmentProducerService) {
		this.appointmentRepository = appointmentRepository;
		this.appointmentProducerService = appointmentProducerService;
	}

	@Transactional(readOnly = true)
	public List<AppointmentDTO> getAppointments() {
		List<Appointment> appointments = appointmentRepository.findAll();

		return appointments.stream().map(AppointmentMapper::toDTO).toList();
	}

	public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
		if (appointmentRepository
				.existsByPractitionerIdAndStartTimeLessThanAndEndTimeGreaterThan(
						appointmentDTO.practitionerId(),
						appointmentDTO.endTime(),
						appointmentDTO.startTime())) {

			throw new AppointmentConflictException(
					"Practitioner already has an appointment at this time"
			);
		}

		Appointment appointment = appointmentRepository.save(AppointmentMapper.toEntity(appointmentDTO));

		appointmentProducerService.sendEvent(new LabTestRequested(
				UUID.randomUUID().toString(),
				appointmentDTO.patientId().toString(),
				appointmentDTO.practitionerId().toString(),
				appointmentDTO.testType(),
				LocalDateTime.now(Clock.systemUTC())
		));

		return AppointmentMapper.toDTO(appointment);
	}

	@Transactional
	public void deleteAppointment(UUID id) {
		appointmentRepository.deleteById(id);
	}

	public AppointmentDTO updateAppointment(UUID id, AppointmentDTO appointmentRequestDTO) {
		throw new UnsupportedOperationException();
	}
}
