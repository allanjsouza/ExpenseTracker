package org.example.service;

import org.example.error.SignUpException;
import org.example.model.User;

public interface UserService {

  String signUp(User user) throws SignUpException;

  User save(User user);

  User findByUsername(String username);
}
