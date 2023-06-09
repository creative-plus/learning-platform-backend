package ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuizQuestion {
  @Id
  @SequenceGenerator(name = "generator", sequenceName = "QUIZ_QUESTION_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
  private int id;

  @Column
  private String text;

  @Column
  private boolean multipleAnswer;

  @OneToMany(mappedBy = "question", orphanRemoval = true)
  private List<QuizQuestionAnswer> answers = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;

  public QuizQuestion() {
  }

  public QuizQuestion(int id, String text, boolean multipleAnswer, List<QuizQuestionAnswer> answers) {
    this.id = id;
    this.text = text;
    this.multipleAnswer = multipleAnswer;
    this.answers = answers;
  }

  public Quiz getQuiz() {
    return quiz;
  }

  public void setQuiz(Quiz quiz) {
    this.quiz = quiz;
  }

  public List<QuizQuestionAnswer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<QuizQuestionAnswer> questionAnswers) {
    this.answers = questionAnswers;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isMultipleAnswer() {
    return multipleAnswer;
  }

  public void setMultipleAnswer(boolean multipleAnswer) {
    this.multipleAnswer = multipleAnswer;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
