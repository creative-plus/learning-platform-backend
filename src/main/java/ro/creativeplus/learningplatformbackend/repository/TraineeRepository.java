package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;

import java.util.Optional;

public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
  Optional<Trainee> findByEmail(String email);
}
