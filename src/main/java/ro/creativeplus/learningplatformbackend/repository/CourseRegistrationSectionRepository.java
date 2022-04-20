package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.CourseRegistrationSection;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationSectionKey;

public interface CourseRegistrationSectionRepository extends JpaRepository<CourseRegistrationSection, CourseRegistrationSectionKey> {
}
