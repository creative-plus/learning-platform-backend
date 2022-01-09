package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.Leaderboard.LeaderboardTrainee;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.repository.QuizAttemptRepository;

import java.util.Comparator;
import java.util.List;
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
    int sectionPoints = courseRegistration.getCourseSections().size();
    int answerPoints = this.quizAttemptRepository
      .findAllByQuiz_Course_IdAndTrainee_Id(courseRegistration.getCourseId(), trainee.getId())
      .stream().map(QuizAttempt::getCorrectAnswers).reduce(0, Integer::sum);
    return new LeaderboardTrainee(trainee, sectionPoints, answerPoints);
  }

  private List<LeaderboardTrainee> fetchLeaderboard(List<Trainee> trainees) {
    return trainees.stream()
      .map(trainee -> {
        int sectionPoints = trainee.getRegistrations().stream()
            .map(courseRegistration -> courseRegistration.getCourseSections().size())
            .reduce(0, Integer::sum);

        int answerPoints = this.quizAttemptRepository.findAllByTrainee_Id(trainee.getId()).stream()
            .map(QuizAttempt::getCorrectAnswers).reduce(0, Integer::sum);

        return new LeaderboardTrainee(trainee, sectionPoints, answerPoints);
      })
      .collect(Collectors.toList());
  }
}

class LeaderboardSorter implements Comparator<LeaderboardTrainee> {
  public int compare(LeaderboardTrainee a, LeaderboardTrainee b) {
    if (b.getAnswerPoints() == a.getAnswerPoints()) {
      return b.getSectionPoints() - a.getSectionPoints();
    }
    return b.getAnswerPoints() - a.getAnswerPoints();
  }
}
