package ro.creativeplus.learningplatformbackend.model;

import ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz.Quiz;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.model.keys.QuizAttemptKey;

import javax.persistence.*;

@Entity
public class QuizAttempt {
  @EmbeddedId
  QuizAttemptKey id;

  @ManyToOne
  @MapsId("traineeId")
  @JoinColumn(name = "trainee_id")
  Trainee trainee;

  @ManyToOne
  @MapsId("quizId")
  @JoinColumn(name = "quiz_id")
  Quiz quiz;

  @Column(nullable = false)
  int correctAnswers;

  public QuizAttempt() {
  }

  public QuizAttempt(QuizAttemptKey id, Trainee trainee, Quiz quiz, int correctAnswers) {
    this.id = id;
    this.trainee = trainee;
    this.quiz = quiz;
    this.correctAnswers = correctAnswers;
  }

  public QuizAttemptKey getId() {
    return id;
  }

  public void setId(QuizAttemptKey id) {
    this.id = id;
  }

  public Trainee getTrainee() {
    return trainee;
  }

  public void setTrainee(Trainee trainee) {
    this.trainee = trainee;
  }

  public Quiz getQuiz() {
    return quiz;
  }

  public void setQuiz(Quiz quiz) {
    this.quiz = quiz;
  }

  public int getCorrectAnswers() {
    return correctAnswers;
  }

  public void setCorrectAnswers(int correctAnswers) {
    this.correctAnswers = correctAnswers;
  }
}
