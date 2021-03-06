package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.creativeplus.learningplatformbackend.dto.auth.ChangePasswordWithTokenDto;
import ro.creativeplus.learningplatformbackend.dto.auth.CheckTokenDto;
import ro.creativeplus.learningplatformbackend.model.auth.AuthRequestEmailPassword;
import ro.creativeplus.learningplatformbackend.model.auth.AuthResponse;
import ro.creativeplus.learningplatformbackend.model.auth.AuthUser;
import ro.creativeplus.learningplatformbackend.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private AuthService authService;

  AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/user")
  ResponseEntity<AuthUser> user() {
    return ResponseEntity.ok().body(authService.getCurrentUser());
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequestEmailPassword authRequest) {
    return ResponseEntity.ok().body(authService.loginWithEmailAndPassword(authRequest));
  }

  @PostMapping("/token/change-password")
  ResponseEntity<AuthResponse> changePassword(@Valid @RequestBody ChangePasswordWithTokenDto request) {
    return ResponseEntity.ok().body(
        this.authService.changePasswordWithActivationToken(request.getToken(), request.getPassword())
    );
  }
  @PostMapping("/token/check")
  ResponseEntity<?> checkToken(@Valid @RequestBody CheckTokenDto request) {
    return ResponseEntity.ok().body(
      this.authService.getEmailWithActivationToken(request.getToken())
    );
  }

}
