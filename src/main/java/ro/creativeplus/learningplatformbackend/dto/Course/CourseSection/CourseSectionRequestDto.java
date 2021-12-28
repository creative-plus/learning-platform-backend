package ro.creativeplus.learningplatformbackend.dto.Course.CourseSection;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class CourseSectionRequestDto {
  private int id;

  @NotEmpty
  private String title;

  @NotEmpty
  @Min(1)
  private int order;

  @NotEmpty
  private String type;

  private String content;

  @AssertTrue
  public boolean crossValidation() {
    if(Objects.equals(this.type, "learning")) {
      return this.content != null;
    }
    return true;
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
}
