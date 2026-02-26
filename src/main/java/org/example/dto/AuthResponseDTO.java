package org.example.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
  private String accessToken;
  private String message;

  public AuthResponseDTO(String accessToken, String message) {
    this.accessToken = accessToken;
    this.message = message;
  }
}
