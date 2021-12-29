package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class QuizQuestionAnswerRequestDto {
  @NotEmpty
  private String text;

  @NotNull
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
