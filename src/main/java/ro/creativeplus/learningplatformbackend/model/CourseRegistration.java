package ro.creativeplus.learningplatformbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
public class CourseRegistration {
  @EmbeddedId
  CourseRegistrationKey id;

  @ManyToOne
  @MapsId("traineeId")
  @JoinColumn(name = "trainee_id")
  Trainee trainee;

  @ManyToOne
  @MapsId("courseId")
  @JoinColumn(name = "course_id")
  Course course;

  @NotEmpty
  Date dateStarted;

  Date dateFinished;


}

@Embeddable
class CourseRegistrationKey implements Serializable {

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
