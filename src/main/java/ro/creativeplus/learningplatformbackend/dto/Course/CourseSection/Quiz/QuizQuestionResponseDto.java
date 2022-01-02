package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz;

import java.util.List;

public class QuizQuestionResponseDto {
  private int id;
  private String text;
  private boolean multipleAnswer;
  private List<QuizQuestionAnswerResponseDto> answers;

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

  public boolean isMultipleAnswer() {
    return multipleAnswer;
  }

  public void setMultipleAnswer(boolean multipleAnswer) {
    this.multipleAnswer = multipleAnswer;
  }

  public List<QuizQuestionAnswerResponseDto> getAnswers() {
    return answers;
  }

  public void setAnswers(List<QuizQuestionAnswerResponseDto> answers) {
    this.answers = answers;
  }
}
