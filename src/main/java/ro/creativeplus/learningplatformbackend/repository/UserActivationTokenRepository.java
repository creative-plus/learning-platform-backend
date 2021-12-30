package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.User.UserActivationToken;

public interface UserActivationTokenRepository extends JpaRepository<UserActivationToken, Integer> {
}
