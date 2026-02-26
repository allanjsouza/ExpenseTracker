package org.example.controller;

import org.example.dto.UserParamsDTO;
import org.example.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired
  private SignUpService signUpService;

  @PostMapping("/signup")
  public String signUp(@RequestBody UserParamsDTO userParams) {
    return signUpService.signUpUser(userParams.getUsername(), userParams.getPassword());
  }
}
