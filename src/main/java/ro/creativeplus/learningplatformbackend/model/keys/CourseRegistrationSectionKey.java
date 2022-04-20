package ro.creativeplus.learningplatformbackend.model.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CourseRegistrationSectionKey implements Serializable {

  public CourseRegistrationSectionKey() {
  }

  public CourseRegistrationSectionKey(int sectionId, CourseRegistrationKey courseRegistrationKey) {
    this.sectionId = sectionId;
    this.courseRegistrationKey = courseRegistrationKey;
  }

  @Column(name = "section_id", nullable = false)
  public int sectionId;

  @Embedded
  public CourseRegistrationKey courseRegistrationKey;

  public int getSectionId() {
    return sectionId;
  }

  public void setSectionId(int sectionId) {
    this.sectionId = sectionId;
  }

  public CourseRegistrationKey getCourseRegistrationKey() {
    return courseRegistrationKey;
  }

  public void setCourseRegistrationKey(CourseRegistrationKey courseRegistrationKey) {
    this.courseRegistrationKey = courseRegistrationKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CourseRegistrationSectionKey that = (CourseRegistrationSectionKey) o;
    return Objects.equals(sectionId, that.sectionId) && Objects.equals(courseRegistrationKey, that.courseRegistrationKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sectionId, courseRegistrationKey);
  }


}
