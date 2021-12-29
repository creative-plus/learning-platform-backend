package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz;

public class QuizQuestionAnswerResponseDto {
  private String text;
  private boolean correct;

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
