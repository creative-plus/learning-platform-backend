package ro.creativeplus.learningplatformbackend.model.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CourseRegistrationKey implements Serializable {

  public CourseRegistrationKey() { }

  public CourseRegistrationKey(int traineeId, int courseId) {
    this.traineeId = traineeId;
    this.courseId = courseId;
  }

  @Column(name = "trainee_id")
  int traineeId;

  @Column(name = "course_id")
  int courseId;

  public int getTraineeId() {
    return traineeId;
  }

  public void setTraineeId(int traineeId) {
    this.traineeId = traineeId;
  }

  public int getCourseId() {
    return courseId;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CourseRegistrationKey that = (CourseRegistrationKey) o;
    return traineeId == that.traineeId && courseId == that.courseId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(traineeId, courseId);
  }
}
