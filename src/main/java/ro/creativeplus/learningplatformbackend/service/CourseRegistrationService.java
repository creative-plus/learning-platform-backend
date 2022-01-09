package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizGivenAnswers.QuizGivenAnswersDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseWithTraineeRegistrationDto;
import ro.creativeplus.learningplatformbackend.exception.*;
import ro.creativeplus.learningplatformbackend.mapper.CourseMapper;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.Quiz;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestion;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestionAnswer;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;
import ro.creativeplus.learningplatformbackend.model.keys.QuizAttemptKey;
import ro.creativeplus.learningplatformbackend.repository.CourseRegistrationRepository;
import ro.creativeplus.learningplatformbackend.repository.QuizAttemptRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseRegistrationService {
  private final CourseRegistrationRepository courseRegistrationRepository;
  private final QuizAttemptRepository quizAttemptRepository;
  private final TraineeService traineeService;
  private final CourseService courseService;
  private final CourseSectionService courseSectionService;
  private final CourseMapper courseMapper;
  private final ProjectService projectService;

  CourseRegistrationService(CourseRegistrationRepository courseRegistrationRepository,
                            QuizAttemptRepository quizAttemptRepository, TraineeService traineeService,
                            CourseService courseService, CourseSectionService courseSectionService,
                            CourseMapper courseMapper, ProjectService projectService) {
    this.courseRegistrationRepository = courseRegistrationRepository;
    this.quizAttemptRepository = quizAttemptRepository;
    this.traineeService = traineeService;
    this.courseService = courseService;
    this.courseSectionService = courseSectionService;
    this.courseMapper = courseMapper;
    this.projectService = projectService;
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

  public CourseRegistration markSectionAsViewed(CourseRegistrationKey key, int sectionId,
                                                Optional<QuizGivenAnswersDto> quizAnswers) {
    CourseRegistration courseRegistration = this.getCourseRegistration(key);
    CourseSection section = this.courseSectionService.getCourseSection(sectionId);
    this.checkSectionViewable(courseRegistration, section);
    Set<CourseSection> sections = courseRegistration.getCourseSections();
    if(courseRegistration.getCourseSections().stream().anyMatch(s -> s.getId() == sectionId)) {
      throw new NotAllowedException("This section was already viewed.");
    }
    if(section instanceof Quiz) {
      this.checkQuizAnswers((Quiz) section, quizAnswers, courseRegistration.getTrainee());
    }
    sections.add(section);
    if(section.getOrderInCourse() == courseRegistration.getCourse().getCourseSections().size() - 1) {
      courseRegistration.setDateFinished(new Date(System.currentTimeMillis()));
    }
    return this.courseRegistrationRepository.save(courseRegistration);
  }

  private void checkQuizAnswers(Quiz quiz, Optional<QuizGivenAnswersDto> quizAnswers, Trainee trainee) {
    List<QuizQuestion> questions = quiz.getQuizQuestions();
    HashMap<Integer, List<Integer>> questionAnswerIds = new HashMap<>();
    HashMap<Integer, Boolean> correctQuestionAnswers = new HashMap<>();
    questions.forEach(question -> {
      List<Integer> correctAnswers = question.getAnswers().stream()
          .filter(QuizQuestionAnswer::isCorrect)
          .map(QuizQuestionAnswer::getId)
          .sorted()
          .collect(Collectors.toList());
      questionAnswerIds.put(question.getId(), correctAnswers);
      correctQuestionAnswers.put(question.getId(), false);
    });
    if(quizAnswers.isEmpty()) {
      throw new WrongQuizAnswerException("Please provide answers for the quiz.", correctQuestionAnswers);
    }
    quizAnswers.get().getAnswers().forEach(quizAnswer -> {
      if(!correctQuestionAnswers.containsKey(quizAnswer.getQuestionId())) {
        throw new NotAllowedException("Provided question doesn't belong to the quiz.");
      }
      List<Integer> givenAnswerIds = quizAnswer.getAnswerIds().stream().sorted().collect(Collectors.toList());
      Boolean answersAreCorrect = givenAnswerIds.equals(questionAnswerIds.get(quizAnswer.getQuestionId()));
      correctQuestionAnswers.put(quizAnswer.getQuestionId(), answersAreCorrect);
    });
    AtomicInteger correctQuestionNumber = new AtomicInteger();
    correctQuestionAnswers.forEach((questionId, correct) -> {
      if(correct) {
        correctQuestionNumber.addAndGet(1);
      }
    });
    List<QuizAttempt> quizAttempts = this.quizAttemptRepository.findAllByQuiz_IdAndTrainee_Id(quiz.getId(), trainee.getId());
    QuizAttemptKey currentAttemptKey = new QuizAttemptKey(trainee.getId(), quiz.getId(), new Timestamp(System.currentTimeMillis()));
    QuizAttempt currentAttempt = new QuizAttempt(currentAttemptKey, trainee, quiz, correctQuestionNumber.get());

    if(correctQuestionNumber.get() == questions.size() || quizAttempts.size() > 0) {
      // We let trainees pass anyway if it's the second attempt
      this.quizAttemptRepository.save(currentAttempt);
      return;
    }
    Integer threshold = quiz.getCorrectAnswersThreshold();
    if(threshold == null) threshold = questions.size();
    if(correctQuestionNumber.get() >= threshold) {
      this.quizAttemptRepository.save(currentAttempt);
      throw new WrongQuizAnswerException("Some or all answers were wrong.", correctQuestionAnswers, 1, true);
    } else {
      this.quizAttemptRepository.save(currentAttempt);
      throw new WrongQuizAnswerException("Some or all answers were wrong.", correctQuestionAnswers, 1, false);
    }

  }

  public CourseSection viewCourseSection(CourseRegistrationKey key, int sectionId) {
    CourseRegistration courseRegistration = this.getCourseRegistration(key);
    CourseSection section = this.courseSectionService.getCourseSection(sectionId);
    this.checkSectionViewable(courseRegistration, section);
    return section;
  }

  private void checkSectionViewable(CourseRegistration courseRegistration, CourseSection section) {
    if(courseRegistration.getCourse().getId() != section.getCourse().getId()) {
      throw new NotAllowedException("This section doesn't belong to the course.");
    }
    if(section.getOrderInCourse() == 0) return;
    courseRegistration.getCourseSections().stream()
        .filter(s -> s.getOrderInCourse() + 1 == section.getOrderInCourse())
        .findFirst()
        .orElseThrow(() -> new ForbiddenException("You cannot access this section."));
  }

  public List<CourseWithTraineeRegistrationDto> getAllCoursesForTrainee(int traineeId) {
    List<Course> courses = this.courseService.getCourses();
    Map<Integer, CourseRegistration> enrolledMap = this.courseRegistrationRepository.findAllByTrainee_Id(traineeId).stream()
        .collect(Collectors.toMap(CourseRegistration::getCourseId, Function.identity()));
    return courses.stream()
        .map(course -> {
          Optional<CourseRegistration> registration = Optional.ofNullable(enrolledMap.get(course.getId()));
          return this.courseMapper.toCourseWithTraineeRegistrationDto(course, registration);
        })
        .collect(Collectors.toList());
  }

  public List<CourseRegistration> getAllForCourse(int courseId) {
    return this.courseRegistrationRepository.findAllByCourse_Id(courseId);
  }

  public List<CourseRegistration> getAllForCourseAndProject(int courseId, int projectId) {
    return this.courseRegistrationRepository
      .findAllByCourse_IdAndTrainee_ProjectsContaining(courseId, this.projectService.getProject(projectId));
  }
}
