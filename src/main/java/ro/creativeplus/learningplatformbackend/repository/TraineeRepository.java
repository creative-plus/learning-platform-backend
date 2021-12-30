package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;

public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
}
