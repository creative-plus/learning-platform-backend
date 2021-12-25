package ro.creativeplus.learningplatformbackend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.JWTUtils;
import ro.creativeplus.learningplatformbackend.model.MyUserDetails;
import ro.creativeplus.learningplatformbackend.model.User;
import ro.creativeplus.learningplatformbackend.model.auth.AuthRequestEmailPassword;
import ro.creativeplus.learningplatformbackend.model.auth.AuthResponse;
import ro.creativeplus.learningplatformbackend.model.auth.AuthUser;
import ro.creativeplus.learningplatformbackend.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {
  private JWTUtils jwtUtils;
  private AuthenticationManager authenticationManager;
  private UserService userService;

  AuthService(JWTUtils jwtUtils, AuthenticationManager authenticationManager, UserService userService) {
    this.jwtUtils = jwtUtils;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
  }

  public AuthResponse loginWithEmailAndPassword(AuthRequestEmailPassword authRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
    );
    return new AuthResponse(jwtUtils.generateToken(authRequest.getEmail()), this.getCurrentUser());
  }

  public AuthUser getCurrentUser() {
    MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    Optional<User> user = userService.findByEmail(username);
    user.orElseThrow(() -> new UsernameNotFoundException("Email not found."));
    String type = userService.getUserType(user.get());
    return new AuthUser(user.get(), type);
  }
}
