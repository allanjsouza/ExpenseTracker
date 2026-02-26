package org.example.service;

import org.example.model.AppUser;
import org.example.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

  @Autowired
  private AppUserRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String signUpUser(String username, String password) {
    if (userRepo.findByUsername(username).isPresent()) {
      return "Error: Username already exists";
    }

    String encodedPassword = passwordEncoder.encode(password);

    AppUser newUser = new AppUser(username, encodedPassword);
    userRepo.save(newUser);

    return "User successfully signed up!";
  }
}
