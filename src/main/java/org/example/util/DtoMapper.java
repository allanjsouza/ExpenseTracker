package org.example.util;

import org.example.dto.AuthDTO;
import org.example.dto.UserDTO;
import org.example.model.User;

public class DtoMapper {

  public static User userFrom(UserDTO userDTO) {
    User result = new User();
    result.setFullName(userDTO.getFullName());
    result.setUsername(userDTO.getUsername());
    result.setPassword(userDTO.getPassword());
    return result;
  }

  public static AuthDTO authDTOFrom(UserDTO userDTO) {
    AuthDTO result = new AuthDTO();
    result.setUsername(userDTO.getUsername());
    result.setPassword(userDTO.getPassword());
    return result;
  }
}
