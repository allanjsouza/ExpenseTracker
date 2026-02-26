package org.example.service;

import org.example.dto.AuthDTO;
import org.example.dto.AuthResponseDTO;
import org.example.dto.UserDTO;

public interface AuthService {
  AuthResponseDTO registerUser(UserDTO user);

  AuthResponseDTO loginUser(AuthDTO auth);
}
