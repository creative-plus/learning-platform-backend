package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz;

public class QuizQuestionAnswerResponseDto {
  private String text;
  private Boolean correct;

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
