package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.creativeplus.learningplatformbackend.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
