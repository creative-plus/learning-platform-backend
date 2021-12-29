package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;

public interface CourseSectionRepository extends JpaRepository<CourseSection, Integer> {
}
