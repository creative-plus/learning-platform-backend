package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.User.User;
import ro.creativeplus.learningplatformbackend.model.User.UserActivationToken;
import ro.creativeplus.learningplatformbackend.repository.UserActivationTokenRepository;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserActivationTokenService {

  private final UserActivationTokenRepository userActivationTokenRepository;
  private static final SecureRandom secureRandom = new SecureRandom();
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

  UserActivationTokenService(UserActivationTokenRepository userActivationTokenRepository) {
    this.userActivationTokenRepository = userActivationTokenRepository;
  }

  public UserActivationToken getUserActivationToken(String token) {
    Optional<UserActivationToken> activationTokenOptional = this.userActivationTokenRepository.findByToken(token);
    if(activationTokenOptional.isEmpty()) {
      throw new ObjectNotFoundException("Invalid token.");
    }
    return activationTokenOptional.get();
  }

  public UserActivationToken setUserActivationTokenUsed(String token) {
    UserActivationToken activationToken = this.getUserActivationToken(token);
    activationToken.setUsed(true);
    return this.userActivationTokenRepository.save(activationToken);
  }


  public UserActivationToken generateUserActivationToken(User user) {
    UserActivationToken activationToken = new UserActivationToken();
    activationToken.setUser(user);
    activationToken.setUsed(false);
    activationToken.setToken(this.generateNewToken());
    return this.userActivationTokenRepository.save(activationToken);
  }

  private String generateNewToken() {
    byte[] randomBytes = new byte[24];
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }
}
