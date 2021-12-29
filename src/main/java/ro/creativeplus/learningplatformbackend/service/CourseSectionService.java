package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.CourseSection;
import ro.creativeplus.learningplatformbackend.model.courseSection.Quiz.Quiz;
import ro.creativeplus.learningplatformbackend.model.courseSection.Quiz.QuizQuestion;
import ro.creativeplus.learningplatformbackend.model.courseSection.Quiz.QuizQuestionAnswer;
import ro.creativeplus.learningplatformbackend.repository.CourseSectionRepository;
import ro.creativeplus.learningplatformbackend.repository.QuizQuestionAnswerRepository;
import ro.creativeplus.learningplatformbackend.repository.QuizQuestionRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseSectionService {

  private final CourseSectionRepository courseSectionRepository;
  private final QuizQuestionRepository quizQuestionRepository;
  private final QuizQuestionAnswerRepository quizQuestionAnswerRepository;

  CourseSectionService(CourseSectionRepository courseSectionRepository,
                       QuizQuestionRepository quizQuestionRepository,
                       QuizQuestionAnswerRepository quizQuestionAnswerRepository) {
    this.courseSectionRepository = courseSectionRepository;
    this.quizQuestionRepository = quizQuestionRepository;
    this.quizQuestionAnswerRepository = quizQuestionAnswerRepository;
  }

  public CourseSection addOrEditCourseSection(CourseSection courseSection) {
      return this.courseSectionRepository.save(courseSection);
  }

  public CourseSection addCourseSection(CourseSection courseSection) {
    if(courseSection.getId() > 0) {
      Optional<CourseSection> existingSection = this.courseSectionRepository.findById(courseSection.getId());
      if(existingSection.isPresent()) {
        throw new ObjectAlreadyExistsException("Section already exists.");
      }
    }
    CourseSection dbCourseSection = this.courseSectionRepository.save(courseSection);
    if(courseSection instanceof Quiz) {
      this.setQuizQuestions((Quiz) courseSection, (Quiz) dbCourseSection);
    }
    return dbCourseSection;
  }

  public CourseSection getCourseSection(int id) {
    if(id <= 0) {
      throw new ObjectNotFoundException("Section does not exist.");
    }
    Optional<CourseSection> existingSection = this.courseSectionRepository.findById(id);
    if(existingSection.isEmpty()) {
      throw new ObjectNotFoundException("Section does not exist.");
    }
    return existingSection.get();
  }

  public CourseSection editCourseSection(CourseSection courseSection) {
    CourseSection existingSection = this.getCourseSection(courseSection.getId());
    CourseSection dbCourseSection = this.courseSectionRepository.save(courseSection);
    if(courseSection instanceof Quiz) {
      this.quizQuestionRepository.deleteAll(((Quiz) existingSection).getQuizQuestions());
      this.setQuizQuestions((Quiz) courseSection, (Quiz) dbCourseSection);
    }
    return dbCourseSection;
  }

  public void deleteCourseSection(CourseSection courseSection) {
    CourseSection existingSection = this.getCourseSection(courseSection.getId());
    this.courseSectionRepository.delete(courseSection);
  }

  private void setQuizQuestions(Quiz courseSection, Quiz dbCourseSection) {
    List<QuizQuestion> quizQuestions = courseSection.getQuizQuestions().stream()
        .map(quizQuestion -> {
          quizQuestion.setQuiz(dbCourseSection);
          QuizQuestion dbQuestion = this.quizQuestionRepository.save(quizQuestion);
          List<QuizQuestionAnswer> answers = quizQuestion.getAnswers().stream()
              .map(answer -> {
                answer.setQuestion(dbQuestion);
                return this.quizQuestionAnswerRepository.save(answer);
              })
              .collect(Collectors.toList());
          dbQuestion.setAnswers(answers);
          return dbQuestion;
        })
        .collect(Collectors.toList());
    dbCourseSection.setQuizQuestions(quizQuestions);
  }
}
