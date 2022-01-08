package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.model.CourseProgress;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;
import ro.creativeplus.learningplatformbackend.repository.QuizAttemptRepository;

import java.util.List;

@Service
public class CourseProgressService {

  private final CourseRegistrationService courseRegistrationService;
  private final QuizAttemptRepository quizAttemptRepository;

  public CourseProgressService(CourseRegistrationService courseRegistrationService, QuizAttemptRepository quizAttemptRepository) {
    this.courseRegistrationService = courseRegistrationService;
    this.quizAttemptRepository = quizAttemptRepository;
  }

  public CourseProgress getCourseProgress(int courseId, int traineeId) {
    CourseRegistration courseRegistration = this.courseRegistrationService
        .getCourseRegistration(new CourseRegistrationKey(traineeId, courseId));

    List<QuizAttempt> quizAttempts = this.quizAttemptRepository.
        findAllByQuiz_Course_IdAndTrainee_Id(courseId, traineeId);

    return new CourseProgress(courseRegistration, quizAttempts);
  }
}
