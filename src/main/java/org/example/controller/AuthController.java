package org.example.controller;

import org.example.dto.UserParamsDTO;
import org.example.security.JwtUtil;
import org.example.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
  private SignUpService signUpService;

  @PostMapping("/login")
  public String login(@RequestBody UserParamsDTO userParams) {
    String username = userParams.getUsername();
    Authentication auth = new UsernamePasswordAuthenticationToken(username, userParams.getPassword());

    try {
      authManager.authenticate(auth);
      return jwtUtil.generateToken(username);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid credentials");
    }
  }

  @PostMapping("/signup")
  public String signUp(@RequestBody UserParamsDTO userParams) {
    return signUpService.signUpUser(userParams.getUsername(), userParams.getPassword());
  }
}
