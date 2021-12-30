package ro.creativeplus.learningplatformbackend.dto.auth;

import javax.validation.constraints.NotEmpty;

public class ChangePasswordWithTokenDto {
  @NotEmpty
  private String token;

  @NotEmpty
  private String password;

  public String getToken() {
    return token;
  }

  public String getPassword() {
    return password;
  }
}
