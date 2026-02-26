package org.example.service;

import org.example.model.User;

public interface UserService {

  User save(User user);

  User findByUsername(String username);
}
