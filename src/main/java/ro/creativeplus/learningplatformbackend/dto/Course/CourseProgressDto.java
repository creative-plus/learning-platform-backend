package ro.creativeplus.learningplatformbackend.dto.Course;

import ro.creativeplus.learningplatformbackend.dto.Course.CourseRegistration.CourseRegistrationDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizAttemptDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizResponseDto;
import ro.creativeplus.learningplatformbackend.model.Course;

import java.util.List;

public class CourseProgressDto {
  private CourseRegistrationDto registration;
  private CourseResponseDto course;
  private List<QuizAttemptDto> quizAttempts;

  public CourseRegistrationDto getRegistration() {
    return registration;
  }

  public void setRegistration(CourseRegistrationDto registration) {
    this.registration = registration;
  }

  public CourseResponseDto getCourse() {
    return course;
  }

  public void setCourse(CourseResponseDto course) {
    this.course = course;
  }

  public List<QuizAttemptDto> getQuizAttempts() {
    return quizAttempts;
  }

  public void setQuizAttempts(List<QuizAttemptDto> quizAttempts) {
    this.quizAttempts = quizAttempts;
  }
}
