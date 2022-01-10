package ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz;

import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz extends CourseSection {

  @Column
  Integer correctAnswersThreshold;

  @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<QuizQuestion> quizQuestions = new ArrayList<>();

  @OneToMany(mappedBy = "quiz")
  List<QuizAttempt> quizAttempts;

  public List<QuizQuestion> getQuizQuestions() {
    return quizQuestions;
  }

  public Quiz() {
  }

  public Quiz(int id, List<QuizQuestion> quizQuestions) {
    this.setId(id);
    this.quizQuestions = quizQuestions;
  }

  public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
    this.quizQuestions = quizQuestions;
  }

  public List<QuizAttempt> getQuizAttempts() {
    return quizAttempts;
  }

  public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
    this.quizAttempts = quizAttempts;
  }

  public Integer getCorrectAnswersThreshold() {
    return correctAnswersThreshold;
  }

  public void setCorrectAnswersThreshold(Integer correctAnswersThreshold) {
    this.correctAnswersThreshold = correctAnswersThreshold;
  }
}
