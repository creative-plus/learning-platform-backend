package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class QuizQuestionRequestDto {
  @NotEmpty
  private String text;

  @NotNull
  private boolean multipleAnswer;

  @NotEmpty
  @Size(min = 2, max = 6)
  private List<@Valid QuizQuestionAnswerRequestDto> answers;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isMultipleAnswer() {
    return multipleAnswer;
  }

  public void setMultipleAnswer(boolean multipleAnswer) {
    this.multipleAnswer = multipleAnswer;
  }

  public List<QuizQuestionAnswerRequestDto> getAnswers() {
    return answers;
  }

  public void setAnswers(List<QuizQuestionAnswerRequestDto> answers) {
    this.answers = answers;
  }
}
