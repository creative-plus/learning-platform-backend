package ro.creativeplus.learningplatformbackend.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizGivenAnswers.QuizGivenAnswersDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizGivenAnswers.QuizGivenQuestionAnswerDto;
import ro.creativeplus.learningplatformbackend.exception.ForbiddenException;
import ro.creativeplus.learningplatformbackend.exception.NotAllowedException;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.WrongQuizAnswerException;
import ro.creativeplus.learningplatformbackend.mapper.CourseMapper;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Learning;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.Quiz;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestion;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestionAnswer;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;
import ro.creativeplus.learningplatformbackend.repository.CourseRegistrationRepository;
import ro.creativeplus.learningplatformbackend.repository.QuizAttemptRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CourseRegistrationServiceTest {

  @InjectMocks
  private CourseRegistrationService courseRegistrationService;

  @Mock
  private CourseRegistrationRepository courseRegistrationRepository;

  @Mock
  private QuizAttemptRepository quizAttemptRepository;

  @Mock
  private TraineeService traineeService;

  @Mock
  private CourseService courseService;

  @Mock
  private CourseSectionService courseSectionService;

  private Trainee trainee;

  private Course course1;

  private CourseRegistration courseRegistrationStartPoint;

  @BeforeEach
  void init() {
    this.trainee = new Trainee();
    this.trainee.setId(1);

    this.course1 = new Course("Course 1", "Description", List.of(
      new Learning(1, "We are studying something useful today, class!"),
      new Quiz(2, List.of(
        new QuizQuestion(1, "How are you today?", false, List.of(
          new QuizQuestionAnswer(1, "Fine!", true),
          new QuizQuestionAnswer(2, "Not fine at all.", false)
        )),
        new QuizQuestion(2, "Do you like doing tests?", true, List.of(
          new QuizQuestionAnswer(3, "Absolutely!", true),
          new QuizQuestionAnswer(4, "Yes.", true),
          new QuizQuestionAnswer(5, "No.", false)
        )),
        new QuizQuestion(3, "Select the ones that are colors:", true, List.of(
          new QuizQuestionAnswer(6, "Red", true),
          new QuizQuestionAnswer(7, "Car", false),
          new QuizQuestionAnswer(8, "Green", true),
          new QuizQuestionAnswer(9, "Blue", true)
        ))
      )),
      new Learning(3, "Some wrapping up info.")
    ));

    this.course1.setId(1);
    this.course1.getCourseSections().get(0).setCourse(this.course1);
    this.course1.getCourseSections().get(0).setOrderInCourse(0);
    this.course1.getCourseSections().get(1).setCourse(this.course1);
    this.course1.getCourseSections().get(1).setOrderInCourse(1);
    ((Quiz) this.course1.getCourseSections().get(1)).setCorrectAnswersThreshold(2);
    this.course1.getCourseSections().get(2).setCourse(this.course1);
    this.course1.getCourseSections().get(2).setOrderInCourse(2);


    this.courseRegistrationStartPoint = new CourseRegistration();
    this.courseRegistrationStartPoint.setId(new CourseRegistrationKey(this.trainee.getId(), this.course1.getId()));
    this.courseRegistrationStartPoint.setCourse(this.course1);
    this.courseRegistrationStartPoint.setTrainee(this.trainee);
    this.courseRegistrationStartPoint.setDateStarted(new Date(System.currentTimeMillis()));
  }

  @Test
  void enrollToCourseNotEnrolled() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());

    when(this.courseService.getCourse(this.course1.getId())).thenReturn(this.course1);
    when(this.traineeService.getTrainee(this.trainee.getId())).thenReturn(this.trainee);
    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.empty());
    when(this.courseRegistrationRepository.save(any(CourseRegistration.class))).then(returnsFirstArg());

    CourseRegistration registration = this.courseRegistrationService.enrollToCourse(key);
    assertEquals(this.course1.getId(), registration.getCourse().getId());
    assertEquals(this.trainee.getId(), registration.getTrainee().getId());
    assertTrue(registration.getCourseSections().isEmpty());
    assertNotNull(registration.getDateStarted());
    assertNull(registration.getDateFinished());
  }

  @Test
  void enrollToCourseAlreadyEnrolled() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());

    when(this.courseService.getCourse(this.course1.getId())).thenReturn(this.course1);
    when(this.traineeService.getTrainee(this.trainee.getId())).thenReturn(this.trainee);
    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(new CourseRegistration()));

    ObjectAlreadyExistsException e = assertThrows(ObjectAlreadyExistsException.class,
      () -> this.courseRegistrationService.enrollToCourse(key));

    assertEquals("Trainee has already enrolled to this course.", e.getMessage());
  }

  @Test
  void viewCourseFirstSection() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 1;

    CourseSection courseSection = this.course1.getCourseSections().get(0);

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);

    CourseSection result = this.courseRegistrationService.viewCourseSection(key, sectionId);

    assertEquals(sectionId, result.getId());
    assertEquals(courseSection.getTitle(), result.getTitle());
  }

  @Test
  void viewCourseSectionJumpOver() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 2;

    CourseSection courseSection = this.course1.getCourseSections().get(1);

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);

    ForbiddenException e = assertThrows(ForbiddenException.class,
      () -> this.courseRegistrationService.viewCourseSection(key, sectionId));

    assertEquals("You cannot access this section.", e.getMessage());
  }

  @Test
  void viewCourseSecondSection() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 2;

    CourseSection courseSection = this.course1.getCourseSections().get(1);

    this.courseRegistrationStartPoint.getCourseSections().add(this.course1.getCourseSections().get(0));

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);

    CourseSection result = this.courseRegistrationService.viewCourseSection(key, sectionId);

    assertEquals(sectionId, result.getId());
    assertEquals(courseSection.getTitle(), result.getTitle());
  }

  @Test
  void passLearningSection() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 1;

    CourseSection courseSection = this.course1.getCourseSections().get(0);

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);
    when(this.courseRegistrationRepository.save(any(CourseRegistration.class))).then(returnsFirstArg());

    CourseRegistration result = this.courseRegistrationService.markSectionAsViewed(key, sectionId, Optional.empty());

    assertEquals(1, result.getCourseSections().size());
    assertEquals(courseSection, result.getCourseSections().iterator().next());
  }

  @Test
  void passSectionTwice() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 1;

    CourseSection courseSection = this.course1.getCourseSections().get(0);

    this.courseRegistrationStartPoint.getCourseSections().add(this.course1.getCourseSections().get(0));

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);

    NotAllowedException e = assertThrows(NotAllowedException.class,
      () -> this.courseRegistrationService.markSectionAsViewed(key, sectionId, Optional.empty()));

    assertEquals("This section was already viewed.", e.getMessage());
  }

  @Test
  void passQuizSectionWithAllAnswersCorrect() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 2;

    CourseSection courseSection = this.course1.getCourseSections().get(1);

    this.courseRegistrationStartPoint.getCourseSections().add(this.course1.getCourseSections().get(0));

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);
    when(this.courseRegistrationRepository.save(any(CourseRegistration.class))).then(returnsFirstArg());

    QuizGivenAnswersDto quizGivenAnswersDto = new QuizGivenAnswersDto(List.of(
      new QuizGivenQuestionAnswerDto(1, List.of(1)),
      new QuizGivenQuestionAnswerDto(2, List.of(3, 4)),
      new QuizGivenQuestionAnswerDto(3, List.of(6, 8, 9))
    ));

    CourseRegistration result = this.courseRegistrationService.markSectionAsViewed(key, sectionId,
      Optional.of(quizGivenAnswersDto));

    assertEquals(2, result.getCourseSections().size());
    result.getCourseSections().forEach(section -> {
      int index = section.getOrderInCourse();
      assertEquals(this.course1.getCourseSections().get(index), section);
    });
  }

  @Test
  @DisplayName("Try to pass quiz with partially correct answers, above threshold")
  void passQuizSectionPartiallyCorrect() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 2;

    CourseSection courseSection = this.course1.getCourseSections().get(1);

    this.courseRegistrationStartPoint.getCourseSections().add(this.course1.getCourseSections().get(0));

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);

    QuizGivenAnswersDto quizGivenAnswersDto = new QuizGivenAnswersDto(List.of(
      new QuizGivenQuestionAnswerDto(1, List.of(1)),
      new QuizGivenQuestionAnswerDto(3, List.of(6, 8, 9))
    ));

    WrongQuizAnswerException e = assertThrows(WrongQuizAnswerException.class,
      () -> this.courseRegistrationService.markSectionAsViewed(key, sectionId, Optional.of(quizGivenAnswersDto)));

    assertEquals("Some or all answers were wrong.", e.getMessage());
    assertTrue(e.isShowCorrectQuestionAnswers());
  }

  @Test
  @DisplayName("Try to pass quiz below threshold")
  void passQuizSectionBelowThreshold() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 2;

    CourseSection courseSection = this.course1.getCourseSections().get(1);

    this.courseRegistrationStartPoint.getCourseSections().add(this.course1.getCourseSections().get(0));

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);

    QuizGivenAnswersDto quizGivenAnswersDto = new QuizGivenAnswersDto(List.of(
      new QuizGivenQuestionAnswerDto(1, List.of(1))
    ));

    WrongQuizAnswerException e = assertThrows(WrongQuizAnswerException.class,
      () -> this.courseRegistrationService.markSectionAsViewed(key, sectionId, Optional.of(quizGivenAnswersDto)));

    assertEquals("Some or all answers were wrong.", e.getMessage());
    assertFalse(e.isShowCorrectQuestionAnswers());
  }

  @Test
  @DisplayName("Allow going further on the second quiz attempt")
  void passQuizSectionIfSecondTry() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 2;

    CourseSection courseSection = this.course1.getCourseSections().get(1);

    this.courseRegistrationStartPoint.getCourseSections().add(this.course1.getCourseSections().get(0));

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);
    when(this.courseRegistrationRepository.save(any(CourseRegistration.class))).then(returnsFirstArg());
    when(this.quizAttemptRepository.findAllByQuiz_IdAndTrainee_Id(sectionId, this.trainee.getId()))
      .thenReturn(List.of(new QuizAttempt()));

    QuizGivenAnswersDto quizGivenAnswersDto = new QuizGivenAnswersDto(List.of(
      new QuizGivenQuestionAnswerDto(1, List.of(1))
    ));

    CourseRegistration result = this.courseRegistrationService.markSectionAsViewed(key, sectionId,
      Optional.of(quizGivenAnswersDto));

    assertEquals(2, result.getCourseSections().size());
    result.getCourseSections().forEach(section -> {
      int index = section.getOrderInCourse();
      assertEquals(this.course1.getCourseSections().get(index), section);
    });
  }

  @Test
  void finishCourse() {
    CourseRegistrationKey key = new CourseRegistrationKey(this.trainee.getId(), this.course1.getId());
    int sectionId = 3;

    CourseSection courseSection = this.course1.getCourseSections().get(2);

    this.courseRegistrationStartPoint.getCourseSections().add(this.course1.getCourseSections().get(0));
    this.courseRegistrationStartPoint.getCourseSections().add(this.course1.getCourseSections().get(1));

    when(this.courseRegistrationRepository.findById(key)).thenReturn(Optional.of(this.courseRegistrationStartPoint));
    when(this.courseSectionService.getCourseSection(sectionId)).thenReturn(courseSection);
    when(this.courseRegistrationRepository.save(any(CourseRegistration.class))).then(returnsFirstArg());

    CourseRegistration result = this.courseRegistrationService.markSectionAsViewed(key, sectionId, Optional.empty());

    assertEquals(3, result.getCourseSections().size());
    result.getCourseSections().forEach(section -> {
      int index = section.getOrderInCourse();
      assertEquals(this.course1.getCourseSections().get(index), section);
    });

    assertNotNull(result.getDateStarted());
    assertNotNull(result.getDateFinished());
  }
}
