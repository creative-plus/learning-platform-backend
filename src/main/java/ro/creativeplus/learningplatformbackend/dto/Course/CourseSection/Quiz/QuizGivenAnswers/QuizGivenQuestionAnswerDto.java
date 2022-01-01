package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizGivenAnswers;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class QuizGivenQuestionAnswerDto {

  @NotNull
  @Min(1)
  private int questionId;

  @NotEmpty
  private List<Integer> answerIds;

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  public List<Integer> getAnswerIds() {
    return answerIds;
  }

  public void setAnswerIds(List<Integer> answerIds) {
    this.answerIds = answerIds;
  }
}
