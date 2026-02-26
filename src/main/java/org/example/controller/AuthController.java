package org.example.controller;

import org.example.dto.AuthResponseDTO;
import org.example.dto.UserDTO;
import org.example.dto.AuthDTO;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponseDTO> signUp(@RequestBody UserDTO userParams) {
    AuthResponseDTO result = authService.registerUser(userParams);
    if (result.getAccessToken() != null)
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    else
      return ResponseEntity.unprocessableEntity().body(result);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authParams) {
    AuthResponseDTO result = authService.loginUser(authParams);
    if (result.getAccessToken() != null)
      return ResponseEntity.ok(result);
    else
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
  }
}
