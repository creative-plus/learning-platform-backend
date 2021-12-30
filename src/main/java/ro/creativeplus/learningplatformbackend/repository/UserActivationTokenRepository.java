package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.User.UserActivationToken;

import java.util.Optional;

public interface UserActivationTokenRepository extends JpaRepository<UserActivationToken, Integer> {
  Optional<UserActivationToken> findByToken(String token);
}
