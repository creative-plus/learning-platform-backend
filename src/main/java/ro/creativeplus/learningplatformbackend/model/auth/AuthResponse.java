package ro.creativeplus.learningplatformbackend.model.auth;

public class AuthResponse {
  private String token;
  private AuthUser user;

  public AuthResponse(String token, AuthUser user) {
    this.token = token;
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public AuthUser getUser() {
    return user;
  }

  public void setUser(AuthUser user) {
    this.user = user;
  }
}
