package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.Quiz;
import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.QuizQuestion;
import ro.creativeplus.learningplatformbackend.repository.CourseSectionRepository;
import ro.creativeplus.learningplatformbackend.repository.QuizQuestionAnswerRepository;
import ro.creativeplus.learningplatformbackend.repository.QuizQuestionRepository;

import java.util.Optional;

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
    if(courseSection instanceof Quiz) {
      this.quizQuestionRepository.deleteAll(((Quiz) existingSection).getQuizQuestions());
      this.setQuizQuestions((Quiz) courseSection, (Quiz) existingSection);
    }
    this.courseSectionRepository.save(courseSection);
    return courseSection;
  }

  public void deleteCourseSection(CourseSection courseSection) {
    CourseSection existingSection = this.getCourseSection(courseSection.getId());
    this.courseSectionRepository.delete(existingSection);
  }

  private void setQuizQuestions(Quiz courseSection, Quiz dbCourseSection) {
    courseSection.getQuizQuestions()
        .forEach(quizQuestion -> {
          quizQuestion.setQuiz(dbCourseSection);
          QuizQuestion dbQuestion = this.quizQuestionRepository.save(quizQuestion);
          quizQuestion.getAnswers()
              .forEach(answer -> {
                answer.setQuestion(dbQuestion);
                this.quizQuestionAnswerRepository.save(answer);
              });
        });
  }
}
