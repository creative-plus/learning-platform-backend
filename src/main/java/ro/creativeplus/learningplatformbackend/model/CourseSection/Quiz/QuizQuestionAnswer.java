package ro.creativeplus.learningplatformbackend.model.CourseSection.Quiz;

import javax.persistence.*;

@Entity
public class QuizQuestionAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "quiz_question_id")
  private QuizQuestion question;

  @Column
  private String text;

  @Column
  private boolean correct;

  public QuizQuestionAnswer() {
  }

  public QuizQuestionAnswer(int id, String text, boolean correct) {
    this.id = id;
    this.text = text;
    this.correct = correct;
  }

  public QuizQuestion getQuestion() {
    return question;
  }

  public void setQuestion(QuizQuestion quizQuestion) {
    this.question = quizQuestion;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isCorrect() {
    return correct;
  }

  public void setCorrect(boolean correct) {
    this.correct = correct;
  }
}
