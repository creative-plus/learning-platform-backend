package ro.creativeplus.learningplatformbackend.service;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.creativeplus.learningplatformbackend.model.Course;
import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Learning;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.Quiz;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestion;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestionAnswer;
import ro.creativeplus.learningplatformbackend.repository.CourseRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

  @InjectMocks
  private CourseService courseService;

  @Mock
  private CourseRepository courseRepository;

  @Mock
  private CourseSectionService courseSectionService;

  @Test
  void addCourse() {
    Learning learningSection = new Learning();
    learningSection.setContent("Learning section content");

    List<CourseSection> courseSections = List.of(learningSection);
    Course course = new Course("Course 1", "Course 1 description", courseSections);

    when(courseSectionService.addCourseSection(learningSection)).thenReturn(learningSection);
    when(courseRepository.save(course)).thenReturn(course);

    Course result = this.courseService.addCourse(course);

    assertEquals(course.getName(), result.getName());
    assertEquals(course.getDescription(), result.getDescription());
    assertEquals(course.getCourseSections().size(), result.getCourseSections().size());
  }

  @Test
  void editCourse() {
    Learning existingLearning = new Learning();
    existingLearning.setContent("Learning section content");
    existingLearning.setId(1);

    Quiz existingQuiz = this._createQuizWithRandomQuestions();

    Quiz newQuiz = this._createQuizWithRandomQuestions();

    Learning editedLearning = new Learning();
    existingLearning.setContent("Hello!!");
    existingLearning.setId(1);

    List<CourseSection> existingSections =  new ArrayList<>(List.of(existingLearning, existingQuiz));
    Course existingCourse = new Course("Course 1", "Course 1 description", existingSections);
    existingCourse.setId(1);
    existingLearning.setCourse(existingCourse);
    List<CourseSection> newSections = new ArrayList<>(List.of(newQuiz));
    Course editedCourse = new Course("Course 3", "New description", newSections);
    editedCourse.setId(1);

    when(courseSectionService.addCourseSection(newQuiz)).thenReturn(newQuiz);
    when(courseRepository.findById(1)).thenReturn(java.util.Optional.of(existingCourse));
    when(courseRepository.save(editedCourse)).thenReturn(editedCourse);

    Course result = this.courseService.editCourse(editedCourse);

    assertEquals(editedCourse.getName(), result.getName());
    assertEquals(editedCourse.getDescription(), result.getDescription());
    assertEquals(editedCourse.getCourseSections().size(), result.getCourseSections().size());
  }

  private Quiz _createQuizWithRandomQuestions() {
    Quiz quiz = new Quiz();
    List<QuizQuestion> questions = new ArrayList<>();
    for(int i = 0; i < 2; i ++) {
      List<QuizQuestionAnswer> quizQuestionAnswers = new ArrayList<>();
      quizQuestionAnswers.add(new QuizQuestionAnswer(i * 2 + 1, "True", true));
      quizQuestionAnswers.add(new QuizQuestionAnswer(i * 2 + 2,"False", false));
      QuizQuestion quizQuestion = new QuizQuestion();
      quizQuestion.setAnswers(quizQuestionAnswers);
      quizQuestion.setMultipleAnswer(false);
      quizQuestion.setText(RandomString.make(16));
      questions.add(quizQuestion);
    }
    quiz.setQuizQuestions(questions);
    return quiz;
  }
}
