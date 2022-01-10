package ro.creativeplus.learningplatformbackend.model;

import java.util.List;

public class CourseProgress {
  private CourseRegistration courseRegistration;
  private Course course;
  private List<QuizAttempt> quizAttempts;

  public CourseProgress(CourseRegistration courseRegistration, Course course, List<QuizAttempt> quizAttempts) {
    this.courseRegistration = courseRegistration;
    this.course = course;
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

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public List<QuizAttempt> getQuizAttempts() {
    return quizAttempts;
  }

  public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
    this.quizAttempts = quizAttempts;
  }
}
