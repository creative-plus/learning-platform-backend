package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection;

import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.Quiz.QuizQuestionRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

public class CourseSectionRequestDto {
  private int id;

  @NotEmpty
  private String title;

  @Min(0)
  private int order;

  @NotEmpty
  private String type;

  private String content;

  private Integer correctAnswersThreshold;

  private List<@Valid QuizQuestionRequestDto> questions;

  @AssertTrue(message = "Please select a correct type and match attributes for it.")
  public boolean isValid() {
    if(Objects.equals(this.type, "learning")) {
      return this.content != null;
    } else if (Objects.equals(this.type, "quiz")) {
      return this.questions != null && this.questions.size() >= 1;
    }
    return false;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<QuizQuestionRequestDto> getQuestions() {
    return questions;
  }

  public void setQuestions(List<QuizQuestionRequestDto> questions) {
    this.questions = questions;
  }

  public Integer getCorrectAnswersThreshold() {
    return correctAnswersThreshold;
  }

  public void setCorrectAnswersThreshold(Integer correctAnswersThreshold) {
    this.correctAnswersThreshold = correctAnswersThreshold;
  }
}
