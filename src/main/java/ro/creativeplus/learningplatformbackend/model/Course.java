package ro.creativeplus.learningplatformbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column()
  @NotEmpty
  private String name;

  @Column()
  private String description;

  @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<CourseSection> courseSections = new ArrayList<>();

  @OneToMany(mappedBy = "course")
  Set<CourseRegistration> registrations;

  public List<CourseSection> getCourseSections() {
    return courseSections;
  }

  public void setCourseSections(List<CourseSection> courseSections) {
    this.courseSections = courseSections;
  }

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
}
