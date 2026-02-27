package org.example.service;

import java.util.Optional;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User save(@NonNull User user) {
        return this.userRepo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepo.findByUsername(username).orElse(null);
    }

    @Override
    public Optional<User> findById(@NonNull Long id) {
        return this.userRepo.findById(id);
    }
}
