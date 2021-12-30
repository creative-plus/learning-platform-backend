package ro.creativeplus.learningplatformbackend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.JWTUtils;
import ro.creativeplus.learningplatformbackend.exception.AuthException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.User.MyUserDetails;
import ro.creativeplus.learningplatformbackend.model.User.User;
import ro.creativeplus.learningplatformbackend.model.User.UserActivationToken;
import ro.creativeplus.learningplatformbackend.model.auth.AuthRequestEmailPassword;
import ro.creativeplus.learningplatformbackend.model.auth.AuthResponse;
import ro.creativeplus.learningplatformbackend.model.auth.AuthUser;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AuthService {
  private final JWTUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final UserActivationTokenService tokenService;

  AuthService(JWTUtils jwtUtils, AuthenticationManager authenticationManager, UserService userService, UserActivationTokenService tokenService) {
    this.jwtUtils = jwtUtils;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.tokenService = tokenService;
  }

  public AuthResponse loginWithEmailAndPassword(AuthRequestEmailPassword authRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
    );
    return new AuthResponse(jwtUtils.generateToken(authRequest.getEmail()), this._getCurrentUser(authentication));
  }

  @Transactional
  public AuthResponse changePasswordWithActivationToken(String token, String newPassword) {
    try {
      UserActivationToken activationToken = this.tokenService.getUserActivationToken(token);
      if(activationToken.isUsed()) throw new AuthException("");
      User user = activationToken.getUser();
      this.userService.changeUserPassword(user, newPassword);
      this.tokenService.setUserActivationTokenUsed(token);
      AuthRequestEmailPassword request = new AuthRequestEmailPassword();
      request.setEmail(user.getEmail());
      request.setPassword(newPassword);
      return this.loginWithEmailAndPassword(request);
    } catch (Exception e) {
      throw new AuthException("Invalid token.");
    }
  }

  private AuthUser _getCurrentUser(Authentication authentication) {
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    Optional<User> user = userService.findByEmail(username);
    user.orElseThrow(() -> new UsernameNotFoundException("Email not found."));
    String type = userService.getUserType(user.get());
    return new AuthUser(user.get(), type);
  }

  public AuthUser getCurrentUser() {
    return this._getCurrentUser(SecurityContextHolder.getContext().getAuthentication());
  }
}
