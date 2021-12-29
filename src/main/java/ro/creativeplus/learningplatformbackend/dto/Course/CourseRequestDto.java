package ro.creativeplus.learningplatformbackend.dto.Course;

import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CourseRequestDto {
  @NotEmpty
  private String name;

  private String description;

  @NotNull
  private List<@Valid CourseSectionRequestDto> sections;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<CourseSectionRequestDto> getSections() {
    return sections;
  }

  public void setSections(List<CourseSectionRequestDto> sections) {
    this.sections = sections;
  }
}
