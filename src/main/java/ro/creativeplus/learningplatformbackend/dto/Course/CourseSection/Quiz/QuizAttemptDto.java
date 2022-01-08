package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz;

import ro.creativeplus.learningplatformbackend.model.QuizAttempt;

import java.sql.Timestamp;

public class QuizAttemptDto {
  int quizId;
  int correctAnswers;
  int totalQuestions;
  Timestamp timestamp;

  public QuizAttemptDto() {
  }

  public QuizAttemptDto(QuizAttempt quizAttempt) {
    this.quizId = quizAttempt.getId().getQuizId();
    this.correctAnswers = quizAttempt.getCorrectAnswers();
    this.totalQuestions = quizAttempt.getQuiz().getQuizQuestions().size();
    this.timestamp = quizAttempt.getId().getTimestamp();
  }

  public int getQuizId() {
    return quizId;
  }

  public void setQuizId(int quizId) {
    this.quizId = quizId;
  }

  public int getCorrectAnswers() {
    return correctAnswers;
  }

  public void setCorrectAnswers(int correctAnswers) {
    this.correctAnswers = correctAnswers;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public int getTotalQuestions() {
    return totalQuestions;
  }

  public void setTotalQuestions(int totalQuestions) {
    this.totalQuestions = totalQuestions;
  }
}
