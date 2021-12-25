package ro.creativeplus.learningplatformbackend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.model.MyUserDetails;
import ro.creativeplus.learningplatformbackend.model.User;
import ro.creativeplus.learningplatformbackend.repository.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
  private final UserService userService;

  MyUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userService.findByEmail(email);
    user.orElseThrow(() -> new UsernameNotFoundException("Email not found."));
    return user.map(MyUserDetails::new).get();
  }
}
