package org.example.controller;

import org.example.dto.AccessTokenResponseDTO;
import org.example.dto.SignUpParamsDTO;
import org.example.error.SignUpException;
import org.example.model.User;
import org.example.dto.AuthParamsDTO;
import org.example.security.JwtUtil;
import org.example.service.UserService;
import org.example.util.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<AccessTokenResponseDTO> signUp(@RequestBody SignUpParamsDTO userParams) {
    AccessTokenResponseDTO result = new AccessTokenResponseDTO();

    try {
      User user = AppUserMapper.appUserFrom(userParams);
      String accessToken = userService.signUp(user);
      result.setAccessToken(accessToken);
      result.setMessage("Signup successfully");
      return ResponseEntity.ok(result);
    } catch (SignUpException e) {
      result.setMessage(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AccessTokenResponseDTO> login(@RequestBody AuthParamsDTO userParams) {
    AccessTokenResponseDTO result = new AccessTokenResponseDTO();
    String username = userParams.getUsername();
    Authentication auth = new UsernamePasswordAuthenticationToken(username, userParams.getPassword());

    try {
      authManager.authenticate(auth);
      result.setAccessToken(jwtUtil.generateToken(username));
      result.setMessage("Login successfull");
      return ResponseEntity.ok(result);
    } catch (AuthenticationException e) {
      result.setMessage("Invalid credentials");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
  }
}
