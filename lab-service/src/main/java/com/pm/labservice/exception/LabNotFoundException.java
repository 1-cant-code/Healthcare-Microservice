package com.pm.labservice.exception;

public class LabNotFoundException extends RuntimeException {

  public LabNotFoundException(String message) {
    super(message);
  }
}
