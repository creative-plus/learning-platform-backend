package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;
import ro.creativeplus.learningplatformbackend.repository.CourseRegistrationRepository;

import java.sql.Date;
import java.util.Optional;

@Service
public class CourseRegistrationService {

  private final CourseRegistrationRepository courseRegistrationRepository;
  private final TraineeService traineeService;
  private final CourseService courseService;

  CourseRegistrationService(CourseRegistrationRepository courseRegistrationRepository, TraineeService traineeService,
                            CourseService courseService) {
    this.courseRegistrationRepository = courseRegistrationRepository;
    this.traineeService = traineeService;
    this.courseService = courseService;
  }

  public CourseRegistration getCourseRegistration(CourseRegistrationKey key) {
    Optional<CourseRegistration> courseRegistration = this.courseRegistrationRepository.findById(key);
    if(courseRegistration.isEmpty()) {
      throw new ObjectNotFoundException("Trainee is not enrolled to this course.");
    }
    return courseRegistration.get();
  }

  public CourseRegistration enrollToCourse(CourseRegistrationKey key) {
    Course course = this.courseService.getCourse(key.getCourseId());
    Trainee trainee = this.traineeService.getTrainee(key.getTraineeId());
    Optional<CourseRegistration> existingCourseRegistration = this.courseRegistrationRepository.findById(key);
    if(existingCourseRegistration.isPresent()) {
      throw new ObjectAlreadyExistsException("Trainee has already enrolled to this course.");
    }
    CourseRegistration courseRegistration = new CourseRegistration();
    courseRegistration.setId(key);
    courseRegistration.setCourse(course);
    courseRegistration.setTrainee(trainee);
    courseRegistration.setDateStarted(new Date(System.currentTimeMillis()));
    return this.courseRegistrationRepository.save(courseRegistration);
  }
}
