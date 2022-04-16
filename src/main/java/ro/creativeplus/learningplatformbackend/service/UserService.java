package ro.creativeplus.learningplatformbackend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.model.User.Trainer;
import ro.creativeplus.learningplatformbackend.model.User.User;
import ro.creativeplus.learningplatformbackend.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;

  UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public String getUserType(User user) {
    String type = null;
    if(user instanceof Trainee) {
      type = "trainee";
    } else if(user instanceof Trainer) {
      type = "trainer";
    }
    return type;
  }

  public User changeUserPassword(User user, String password) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(password));
    return this.userRepository.save(user);
  }
}
