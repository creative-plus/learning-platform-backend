package ro.creativeplus.learningplatformbackend.dto.Course;

import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionRequestDto;
import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class CourseRequestDto {
  @NotEmpty
  private String name;

  private String description;

  private int mediaId;

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

  public int getMediaId() {
    return mediaId;
  }

  public void setMediaId(int mediaId) {
    this.mediaId = mediaId;
  }

  public List<CourseSectionRequestDto> getSections() {
    return sections;
  }

  public void setSections(List<CourseSectionRequestDto> sections) {
    this.sections = sections;
  }

  @AssertTrue(message = "Check section order.")
  public boolean isSectionInOrder() {
    int current = 0;

    List<Integer> orders = this.sections.stream()
        .map(CourseSectionRequestDto::getOrder).sorted().collect(Collectors.toList());

    for(Integer order : orders) {
      if(order != current) return false;
      current++;
    }
    return true;
  }
}
