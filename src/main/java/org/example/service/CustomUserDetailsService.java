package org.example.service;

import org.example.model.AppUser;
import org.example.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private AppUserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = userRepo.findByUsername(username)
        .orElseThrow(CustomUserDetailsService::userNotFoundError);

    return User
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
