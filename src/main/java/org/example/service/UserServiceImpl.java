package org.example.service;

import org.example.error.SignUpException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  public UserServiceImpl(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public String signUp(User user) throws SignUpException {
    if (userRepo.findByUsername(user.getUsername()).isPresent())
      throw new SignUpException("Username has already been taken");

    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    userRepo.save(user);

    return jwtUtil.generateToken(user.getUsername());
  }

  @Override
  public User save(@NonNull User user) {
    return this.userRepo.save(user);
  }

  @Override
  public User findByUsername(String username) {
    return this.userRepo.findByUsername(username).orElse(null);
  }
}
