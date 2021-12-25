package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.model.Trainee;
import ro.creativeplus.learningplatformbackend.model.Trainer;
import ro.creativeplus.learningplatformbackend.model.User;
import ro.creativeplus.learningplatformbackend.repository.UserRepository;

import java.util.List;
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
}
