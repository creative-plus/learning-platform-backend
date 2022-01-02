package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz;

public class QuizQuestionAnswerResponseDto {
  private int id;
  private String text;
  private Boolean correct;

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

  public Boolean isCorrect() {
    return correct;
  }

  public void setCorrect(Boolean correct) {
    this.correct = correct;
  }
}
