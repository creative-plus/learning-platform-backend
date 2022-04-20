package ro.creativeplus.learningplatformbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ro.creativeplus.learningplatformbackend.model.User.Trainee;
import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;
import ro.creativeplus.learningplatformbackend.model.keys.CourseRegistrationKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

  @NotNull
  Date dateStarted;

  Date dateFinished;

  @OneToMany(mappedBy = "courseRegistration", orphanRemoval = true)
  private List<CourseRegistrationSection> sections = new ArrayList<>();

  public CourseRegistrationKey getId() {
    return id;
  }

  public int getCourseId() {
    return this.id.getCourseId();
  }

  public void setId(CourseRegistrationKey id) {
    this.id = id;
  }

  public Trainee getTrainee() {
    return trainee;
  }

  public void setTrainee(Trainee trainee) {
    this.trainee = trainee;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
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

  public List<CourseRegistrationSection> getSections() {
    return sections;
  }

  public void setSections(List<CourseRegistrationSection> sections) {
    this.sections = sections;
  }
}
