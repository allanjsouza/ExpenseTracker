package org.example.util;

import org.example.dto.SignUpParamsDTO;
import org.example.model.User;

public class AppUserMapper {

  public static User appUserFrom(SignUpParamsDTO signUpParams) {
    User result = new User();
    result.setFullName(signUpParams.getFullName());
    result.setUsername(signUpParams.getUsername());
    result.setPassword(signUpParams.getPassword());
    return result;
  }
}
