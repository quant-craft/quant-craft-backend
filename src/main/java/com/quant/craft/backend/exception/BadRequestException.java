package com.quant.craft.backend.exception;

public class BadRequestException extends NetworkException {

  public BadRequestException(String message) {
    super(message);
  }
}
