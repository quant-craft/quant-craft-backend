package com.quant.craft.backend.exception;

public class UnauthorizedException extends NetworkException {

  public UnauthorizedException(String message) {
    super(message);
  }
}
