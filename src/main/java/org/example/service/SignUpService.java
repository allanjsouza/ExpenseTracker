package org.example.service;

import org.example.model.User;
import org.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  public String signUpUser(User user) {
    if (userService.findByUsername(user.getUsername()) != null)
      throw new RuntimeException("Username has already been taken");

    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    userService.save(user);

    return jwtUtil.generateToken(user.getUsername());
  }
}
