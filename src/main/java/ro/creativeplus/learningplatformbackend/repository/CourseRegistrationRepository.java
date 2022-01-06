package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;

import java.util.List;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, CourseRegistrationKey> {
  List<CourseRegistration> findAllByTrainee_Id(int traineeId);
}
