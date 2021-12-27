package ro.creativeplus.learningplatformbackend.model.courseSection.Quiz;

import ro.creativeplus.learningplatformbackend.model.CourseSection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz extends CourseSection {
  @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<QuizQuestion> quizQuestions = new ArrayList<>();

  public List<QuizQuestion> getQuizQuestions() {
    return quizQuestions;
  }

  public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
    this.quizQuestions = quizQuestions;
  }
}
