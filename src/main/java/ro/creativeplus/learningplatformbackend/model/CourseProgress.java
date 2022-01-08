package ro.creativeplus.learningplatformbackend.model;

import java.util.List;

public class CourseProgress {
  private CourseRegistration courseRegistration;
  private List<QuizAttempt> quizAttempts;

  public CourseProgress(CourseRegistration courseRegistration, List<QuizAttempt> quizAttempts) {
    this.courseRegistration = courseRegistration;
    this.quizAttempts = quizAttempts;
  }

  public CourseProgress() {
  }

  public CourseRegistration getCourseRegistration() {
    return courseRegistration;
  }

  public void setCourseRegistration(CourseRegistration courseRegistration) {
    this.courseRegistration = courseRegistration;
  }

  public List<QuizAttempt> getQuizAttempts() {
    return quizAttempts;
  }

  public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
    this.quizAttempts = quizAttempts;
  }
}
