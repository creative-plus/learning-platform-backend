package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, CourseRegistrationKey> {
}
