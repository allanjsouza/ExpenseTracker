package org.example.service;

import org.example.dto.AuthDTO;
import org.example.dto.AuthResponseDTO;
import org.example.dto.UserDTO;
import org.example.model.Role;
import org.example.model.User;
import org.example.util.DtoMapper;
import org.example.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authManager;
  private final JwtUtil jwtUtil;

  public AuthServiceImpl(
      UserService userService,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authManager,
      JwtUtil jwtUtil) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.authManager = authManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public AuthResponseDTO registerUser(UserDTO user) {
    if (userService.findByUsername(user.getUsername()) != null)
      return new AuthResponseDTO(null, "Username has already been taken");

    String encodedPassword = passwordEncoder.encode(user.getPassword());
    User newUser = DtoMapper.userFrom(user);
    newUser.setPassword(encodedPassword);
    newUser.setRole(Role.USER);
    userService.save(newUser);
    AuthDTO authDTO = DtoMapper.authDTOFrom(user);

    return loginUser(authDTO);
  }

  @Override
  public AuthResponseDTO loginUser(AuthDTO authParams) {
    String username = authParams.getUsername();
    Authentication auth = new UsernamePasswordAuthenticationToken(username, authParams.getPassword());

    try {
      authManager.authenticate(auth);
      return new AuthResponseDTO(jwtUtil.generateToken(username), "SUCCESS");
    } catch (AuthenticationException e) {
      return new AuthResponseDTO(null, "Invalid credentials");
    }
  }

}
