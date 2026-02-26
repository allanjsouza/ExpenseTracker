package org.example.dto;

import lombok.Data;

@Data
public class AccessTokenResponseDTO {
  private String accessToken;
  private String message;
}
