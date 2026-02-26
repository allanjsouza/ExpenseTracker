package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User appUser = userRepo.findByUsername(username)
        .orElseThrow(CustomUserDetailsService::userNotFoundError);

    return org.springframework.security.core.userdetails.User
        .builder()
        .username(appUser.getUsername())
        .password(appUser.getPassword())
        .roles("USER")
        .build();
  }

  private static UsernameNotFoundException userNotFoundError() {
    return new UsernameNotFoundException("User not found");
  }

}
