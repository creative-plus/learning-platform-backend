package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizGivenAnswers;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class QuizGivenAnswersDto {

  @NotEmpty
  private List<@Valid QuizGivenQuestionAnswerDto> answers;

  public List<QuizGivenQuestionAnswerDto> getAnswers() {
    return answers;
  }

  public void setAnswers(List<QuizGivenQuestionAnswerDto> answers) {
    this.answers = answers;
  }
}
