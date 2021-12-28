package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection;

import javax.validation.constraints.NotEmpty;

public class LearningResponseDto extends CourseSectionResponseDto {
  private final String type = "learning";
  @NotEmpty
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String getType() {
    return type;
  }
}
