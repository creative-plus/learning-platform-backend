package ro.creativeplus.learningplatformbackend.model;

import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationSectionKey;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CourseRegistrationSection {
  @EmbeddedId
  CourseRegistrationSectionKey id;

  @ManyToOne
  @MapsId("sectionId")
  @JoinColumn(name = "section_id")
  CourseSection section;

  @ManyToOne
  @MapsId("courseRegistrationKey")
  @JoinColumns({
    @JoinColumn(name="course_id", referencedColumnName="course_id"),
    @JoinColumn(name="trainee_id", referencedColumnName="trainee_id")
  })
  CourseRegistration courseRegistration;

  Date datePassed;

  public CourseRegistrationSectionKey getId() {
    return id;
  }

  public void setId(CourseRegistrationSectionKey id) {
    this.id = id;
  }

  public CourseSection getSection() {
    return section;
  }

  public void setSection(CourseSection section) {
    this.section = section;
  }

  public CourseRegistration getCourseRegistration() {
    return courseRegistration;
  }

  public void setCourseRegistration(CourseRegistration courseRegistration) {
    this.courseRegistration = courseRegistration;
  }

  public Date getDatePassed() {
    return datePassed;
  }

  public void setDatePassed(Date datePassed) {
    this.datePassed = datePassed;
  }
}
