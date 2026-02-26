package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  public String signUpUser(User user) {
    if (userRepo.findByUsername(user.getUsername()).isPresent())
      throw new RuntimeException("Username has already been taken");

    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    userRepo.save(user);

    return jwtUtil.generateToken(user.getUsername());
  }
}
