package com.pm.appointment.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import com.pm.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

	boolean existsByPractitionerIdAndStartTimeLessThanAndEndTimeGreaterThan(
			UUID uuid,
			LocalDateTime localDateTime,
			LocalDateTime localDateTime1);
}
