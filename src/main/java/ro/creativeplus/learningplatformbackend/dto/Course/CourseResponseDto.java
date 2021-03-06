package ro.creativeplus.learningplatformbackend.dto.Course;

import ro.creativeplus.learningplatformbackend.dto.Course.CourseSection.CourseSectionResponseDto;

import java.util.List;

public class CourseResponseDto {
  private int id;
  private String name;
  private String description;
  private String coverImage;
  private int sectionNumber;

  private List<CourseSectionResponseDto> sections;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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

  public String getCoverImage() {
    return coverImage;
  }

  public void setCoverImage(String coverImage) {
    this.coverImage = coverImage;
  }

  public int getSectionNumber() {
    return sectionNumber;
  }

  public void setSectionNumber(int sectionNumber) {
    this.sectionNumber = sectionNumber;
  }

  public List<CourseSectionResponseDto> getSections() {
    return sections;
  }

  public void setSections(List<CourseSectionResponseDto> sections) {
    this.sections = sections;
  }
}
