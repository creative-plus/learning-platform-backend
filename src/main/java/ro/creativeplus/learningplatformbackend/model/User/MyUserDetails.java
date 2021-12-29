package ro.creativeplus.learningplatformbackend.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.model.User.Trainer;
import ro.creativeplus.learningplatformbackend.model.User.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

  private String username;
  private final String password;
  private final boolean active;
  private final List<GrantedAuthority> authorities;

  public MyUserDetails(User user) {
    this.username = user.getEmail();
    this.password = user.getPassword();
    this.active = user.isActive();
    List<GrantedAuthority> allAuthorities = Arrays.asList(
        new SimpleGrantedAuthority("TRAINEE"),
        new SimpleGrantedAuthority("TRAINER")
    );
    if(user instanceof Trainee) {
      this.authorities = List.of(allAuthorities.get(0));
    } else if(user instanceof Trainer) {
      this.authorities = List.of(allAuthorities.get(1));
    } else {
      this.authorities = allAuthorities;
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }
}
