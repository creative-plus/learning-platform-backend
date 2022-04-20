package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.Leaderboard.LeaderboardTrainee;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.repository.QuizAttemptRepository;
import ro.creativeplus.learningplatformbackend.utils.LeaderboardSorter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {
  private final CourseRegistrationService courseRegistrationService;
  private final ProjectService projectService;
  private final TraineeService traineeService;
  private final QuizAttemptRepository quizAttemptRepository;
  private final LeaderboardSorter sorter;

  public LeaderboardService(CourseRegistrationService courseRegistrationService, ProjectService projectService,
                            TraineeService traineeService, QuizAttemptRepository quizAttemptRepository) {
    this.courseRegistrationService = courseRegistrationService;
    this.projectService = projectService;
    this.traineeService = traineeService;
    this.quizAttemptRepository = quizAttemptRepository;
    this.sorter = new LeaderboardSorter();
  }

  public List<LeaderboardTrainee> getLeaderboard() {
    return this.fetchLeaderboard(this.traineeService.getTrainees());
  }

  public List<LeaderboardTrainee> getLeaderboardForProject(int projectId) {
    return this.fetchLeaderboard(this.projectService.getProject(projectId).getTrainees());
  }

  public List<LeaderboardTrainee> getLeaderboardForCourse(int courseId) {
    return this.courseRegistrationService.getAllForCourse(courseId).stream()
      .map(this::resolveForCourse)
      .sorted(this.sorter)
      .collect(Collectors.toList());
  }

  public List<LeaderboardTrainee> getLeaderboardForProjectAndCourse(int projectId, int courseId) {
    return this.courseRegistrationService.getAllForCourseAndProject(courseId, projectId).stream()
      .map(this::resolveForCourse)
      .sorted(this.sorter)
      .collect(Collectors.toList());
  }

  private LeaderboardTrainee resolveForCourse(CourseRegistration courseRegistration) {
    Trainee trainee = courseRegistration.getTrainee();
    int sectionPoints = courseRegistration.getSections().size();
    int answerPoints = this.calculateQuizPoints(
      this.quizAttemptRepository.findAllByQuiz_Course_IdAndTrainee_Id(courseRegistration.getCourseId(), trainee.getId())
    );
    return new LeaderboardTrainee(trainee, sectionPoints, answerPoints);
  }

  private List<LeaderboardTrainee> fetchLeaderboard(List<Trainee> trainees) {
    return trainees.stream()
      .map(trainee -> {
        int sectionPoints = trainee.getRegistrations().stream()
            .map(courseRegistration -> courseRegistration.getSections().size())
            .reduce(0, Integer::sum);

        int answerPoints = this.calculateQuizPoints(this.quizAttemptRepository.findAllByTrainee_Id(trainee.getId()));

        return new LeaderboardTrainee(trainee, sectionPoints, answerPoints);
      })
      .collect(Collectors.toList());
  }

  private int calculateQuizPoints(List<QuizAttempt> attempts) {
    HashMap<Integer, Integer> maxCorrectAnswerByQuiz = new HashMap<>();
    attempts.forEach(quizAttempt -> {
      int quizId = quizAttempt.getId().getQuizId();
      if(!maxCorrectAnswerByQuiz.containsKey(quizId) || maxCorrectAnswerByQuiz.get(quizId) < quizAttempt.getCorrectAnswers()) {
        maxCorrectAnswerByQuiz.put(quizId, quizAttempt.getCorrectAnswers());
      }
    });
    AtomicInteger result = new AtomicInteger();
    maxCorrectAnswerByQuiz.forEach((k, v) -> result.addAndGet(v));
    return result.get();
  }
}
