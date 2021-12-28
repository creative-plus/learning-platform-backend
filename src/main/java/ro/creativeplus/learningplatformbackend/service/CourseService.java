package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

  private CourseRepository courseRepository;

  CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public List<Course> getCourses() {
    return this.courseRepository.findAll();
  }

  public Course getCourse(int id) {
    Optional<Course> courseOptional = this.courseRepository.findById(id);
    if(courseOptional.isEmpty()) {
      throw new ObjectNotFoundException("The course was not found.");
    }
    return courseOptional.get();
  }

  public Course addCourse(Course course) {
    if(course.getId() > 0) {
      Optional<Course> existingCourse = courseRepository.findById(course.getId());
      if(existingCourse.isPresent()) {
        throw new ObjectAlreadyExistsException("Course already exists.");
      }
    }
    return courseRepository.save(course);
  }


}
