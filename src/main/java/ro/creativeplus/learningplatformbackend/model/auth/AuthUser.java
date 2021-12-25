package ro.creativeplus.learningplatformbackend.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ro.creativeplus.learningplatformbackend.model.User;

public class AuthUser extends User {
  private String type;

  @JsonIgnore
  private boolean active;
  @JsonIgnore
  private boolean password;

  public AuthUser(User user) {
    this.setId(user.getId());
    this.setEmail(user.getEmail());
    this.setFirstName(user.getFirstName());
    this.setLastName(user.getLastName());
    this.setEmail(user.getEmail());
    this.setPhoneNumber(user.getPhoneNumber());
  }

  public AuthUser(User user, String type) {
    this(user);
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
