package org.example.util;

import org.example.dto.SignUpParamsDTO;
import org.example.model.AppUser;

public class AppUserMapper {

  public static AppUser appUserFrom(SignUpParamsDTO signUpParams) {
    AppUser result = new AppUser();
    result.setFullName(signUpParams.getFullName());
    result.setUsername(signUpParams.getUsername());
    result.setPassword(signUpParams.getPassword());
    return result;
  }
}
