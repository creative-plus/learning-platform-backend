package ro.creativeplus.learningplatformbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class CourseSection {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  @NotEmpty
  private String title;

  @Min(1)
  @Column()
  private int orderInCourse;

  @ManyToOne(optional = false)
  @JoinColumn(name = "course_id")
  @JsonIgnore
  private Course course;

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    CourseSection that = (CourseSection) o;
    return id != 0 && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
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

  public int getOrderInCourse() {
    return orderInCourse;
  }

  public void setOrderInCourse(int orderInCourse) {
    this.orderInCourse = orderInCourse;
  }
}
