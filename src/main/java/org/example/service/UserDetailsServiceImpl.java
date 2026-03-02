package org.example.service;

import java.util.Collections;

import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username)
        .orElseThrow(UserDetailsServiceImpl::userNotFoundError);

    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

    return org.springframework.security.core.userdetails.User
        .builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(Role.USER.name())
        .authorities(Collections.singleton(authority))
        .build();
  }

  private static UsernameNotFoundException userNotFoundError() {
    return new UsernameNotFoundException("User not found");
  }

}
