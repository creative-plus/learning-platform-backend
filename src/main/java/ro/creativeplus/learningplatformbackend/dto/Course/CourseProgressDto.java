package ro.creativeplus.learningplatformbackend.dto.Course;

import ro.creativeplus.learningplatformbackend.dto.Course.CourseRegistration.CourseRegistrationDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizAttemptDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizResponseDto;

import java.util.List;

public class CourseProgressDto {
  private CourseRegistrationDto registration;
  private List<QuizAttemptDto> quizAttempts;

  public CourseRegistrationDto getRegistration() {
    return registration;
  }

  public void setRegistration(CourseRegistrationDto registration) {
    this.registration = registration;
  }

  public List<QuizAttemptDto> getQuizAttempts() {
    return quizAttempts;
  }

  public void setQuizAttempts(List<QuizAttemptDto> quizAttempts) {
    this.quizAttempts = quizAttempts;
  }
}
