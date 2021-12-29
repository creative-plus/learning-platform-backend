package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ro.creativeplus.learningplatformbackend.model.auth.AuthRequestEmailPassword;
import ro.creativeplus.learningplatformbackend.model.auth.AuthResponse;
import ro.creativeplus.learningplatformbackend.model.auth.AuthUser;
import ro.creativeplus.learningplatformbackend.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private AuthService authService;
  private AuthenticationManager authenticationManager;

  AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/user")
  ResponseEntity<AuthUser> user() {
    return ResponseEntity.ok().body(authService.getCurrentUser());
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequestEmailPassword authRequest) {
    System.out.println(authRequest.getEmail());
    return ResponseEntity.ok().body(authService.loginWithEmailAndPassword(authRequest));
  }


}
