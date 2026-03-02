package org.example.service;

import java.util.List;
import java.util.Optional;

import org.example.model.User;

public interface UserService {

    User save(User user);

    List<User> getAllUsers();

    User findByUsername(String username);

    Optional<User> findById(Long id);

}
