package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.creativeplus.learningplatformbackend.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
  @Modifying
  @Query("update Course c set c.name = :name, c.description = :description where c.id = :id")
  int updateCourseInfo(@Param("id") int id, @Param("name") String name,
                                 @Param("description") String description);
}
