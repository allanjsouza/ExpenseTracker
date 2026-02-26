package org.example.error;

public class SignUpException extends Exception {
  public SignUpException(String errorMessage) {
    super(errorMessage);
  }
}
