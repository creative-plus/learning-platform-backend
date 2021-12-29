package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseSection;
import ro.creativeplus.learningplatformbackend.repository.CourseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseService {

  private final CourseRepository courseRepository;
  private final CourseSectionService courseSectionService;

  CourseService(CourseRepository courseRepository, CourseSectionService courseSectionService) {
    this.courseRepository = courseRepository;
    this.courseSectionService = courseSectionService;
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

  @Transactional
  public Course addCourse(Course course) {
    if(course.getId() > 0) {
      Optional<Course> existingCourse = this.courseRepository.findById(course.getId());
      if(existingCourse.isPresent()) {
        throw new ObjectAlreadyExistsException("Course already exists.");
      }
    }
    Course dbCourse = this.courseRepository.save(course);
    course.getCourseSections().stream()
        .peek(section -> section.setCourse(dbCourse))
        .forEach(this.courseSectionService::addCourseSection);

    return dbCourse;
  }

  @Transactional
  public Course editCourse(Course course) {
    Course existingCourse = this.getCourse(course.getId());

    // Delete missing
    existingCourse.getCourseSections().stream()
        .filter(section -> !course.getCourseSections().contains(section))
        .forEach(this.courseSectionService::deleteCourseSection);

    // Edit existing
    course.getCourseSections().stream()
        .filter(section -> section.getId() > 0)
        .peek(section -> section.setCourse(course))
        .forEach(this.courseSectionService::editCourseSection);

    // Add new ones
    course.getCourseSections().stream()
        .filter(section -> section.getId() <= 0)
        .peek(section -> section.setCourse(course))
        .forEach(this.courseSectionService::addCourseSection);

    this.courseRepository.save(course);
    return course;
  }

  public void deleteCourseById(int id) {
    this.courseRepository.deleteById(id);
  }

}
