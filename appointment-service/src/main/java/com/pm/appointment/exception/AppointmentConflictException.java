package com.pm.appointment.exception;

public class AppointmentConflictException extends RuntimeException {

  public AppointmentConflictException(String message) {
    super(message);
  }
}
