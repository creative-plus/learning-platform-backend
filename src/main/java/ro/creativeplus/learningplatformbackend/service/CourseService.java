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

@Service
public class CourseService {

  private final CourseRepository courseRepository;
  private CourseSectionService courseSectionService;

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
    List<CourseSection> courseSections = course.getCourseSections().stream()
        .peek(section -> section.setCourse(dbCourse))
        .map(this.courseSectionService::addCourseSection)
        .collect(Collectors.toList());
    dbCourse.setCourseSections(courseSections);
    return dbCourse;
  }


}
