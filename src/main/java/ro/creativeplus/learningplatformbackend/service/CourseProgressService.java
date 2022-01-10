package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.model.CourseProgress;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;
import ro.creativeplus.learningplatformbackend.repository.QuizAttemptRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseProgressService {

  private final CourseRegistrationService courseRegistrationService;
  private final QuizAttemptRepository quizAttemptRepository;

  public CourseProgressService(CourseRegistrationService courseRegistrationService, QuizAttemptRepository quizAttemptRepository) {
    this.courseRegistrationService = courseRegistrationService;
    this.quizAttemptRepository = quizAttemptRepository;
  }

  public List<CourseProgress> getProgress(int traineeId) {
    List<CourseRegistration> courseRegistrations = this.courseRegistrationService
      .getAllForTrainee(traineeId);

    List<QuizAttempt> quizAttempts = this.quizAttemptRepository.
      findAllByTrainee_Id(traineeId);

    HashMap<Integer, List<QuizAttempt>> quizAttemptsByCourse = new HashMap<>();
    quizAttempts.forEach(quizAttempt -> {
      int courseId = quizAttempt.getQuiz().getCourse().getId();
      if(!quizAttemptsByCourse.containsKey(courseId)) {
        quizAttemptsByCourse.put(courseId, new ArrayList<>(List.of(quizAttempt)));
      } else {
        quizAttemptsByCourse.get(courseId).add(quizAttempt);
      }
    });
    return courseRegistrations.stream()
      .map(courseRegistration -> {
        List<QuizAttempt> quizAttemptsInCourse = new ArrayList<>();
        if(quizAttemptsByCourse.containsKey(courseRegistration.getCourseId())) {
          quizAttemptsInCourse = quizAttemptsByCourse.get(courseRegistration.getCourseId());
        }
        return new CourseProgress(courseRegistration, courseRegistration.getCourse(), quizAttemptsInCourse);
      })
      .collect(Collectors.toList());
  }

  public CourseProgress getCourseProgress(int courseId, int traineeId) {
    CourseRegistration courseRegistration = this.courseRegistrationService
        .getCourseRegistration(new CourseRegistrationKey(traineeId, courseId));

    List<QuizAttempt> quizAttempts = this.quizAttemptRepository.
        findAllByQuiz_Course_IdAndTrainee_Id(courseId, traineeId);

    return new CourseProgress(courseRegistration, courseRegistration.getCourse(), quizAttempts);
  }
}
