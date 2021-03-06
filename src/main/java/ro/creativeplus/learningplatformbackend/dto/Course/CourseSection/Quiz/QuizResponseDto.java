package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz;

import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionResponseDto;

import java.util.List;

public class QuizResponseDto extends CourseSectionResponseDto {
  private final String type = "quiz";
  private Integer correctAnswersThreshold;
  private List<QuizQuestionResponseDto> questions;

  @Override
  public String getType() {
    return type;
  }

  public List<QuizQuestionResponseDto> getQuestions() {
    return questions;
  }

  public void setQuestions(List<QuizQuestionResponseDto> questions) {
    this.questions = questions;
  }

  public Integer getCorrectAnswersThreshold() {
    return correctAnswersThreshold;
  }

  public void setCorrectAnswersThreshold(Integer correctAnswersThreshold) {
    this.correctAnswersThreshold = correctAnswersThreshold;
  }
}
