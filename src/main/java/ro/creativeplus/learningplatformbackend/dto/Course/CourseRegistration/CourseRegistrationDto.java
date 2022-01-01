package ro.creativeplus.learningplatformbackend.dto.Course.CourseRegistration;

import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;

import java.sql.Date;
import java.util.List;

public class CourseRegistrationDto {
  private CourseRegistrationKey id;
  private Date dateStarted;
  private Date dateFinished;
  private List<CourseRegistrationSectionDto> sections;

  public CourseRegistrationKey getId() {
    return id;
  }

  public void setId(CourseRegistrationKey id) {
    this.id = id;
  }

  public Date getDateStarted() {
    return dateStarted;
  }

  public void setDateStarted(Date dateStarted) {
    this.dateStarted = dateStarted;
  }

  public Date getDateFinished() {
    return dateFinished;
  }

  public void setDateFinished(Date dateFinished) {
    this.dateFinished = dateFinished;
  }

  public List<CourseRegistrationSectionDto> getSections() {
    return sections;
  }

  public void setSections(List<CourseRegistrationSectionDto> sections) {
    this.sections = sections;
  }
}
