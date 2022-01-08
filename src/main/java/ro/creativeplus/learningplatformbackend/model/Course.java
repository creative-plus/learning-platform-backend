package ro.creativeplus.learningplatformbackend.model;

import ro.creativeplus.learningplatformbackend.model.CourseSection.CourseSection;

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
  @OrderBy("orderInCourse")
  private List<CourseSection> courseSections = new ArrayList<>();

  @OneToMany(mappedBy = "course")
  Set<CourseRegistration> registrations;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cover_image_id")
  private Media coverImage;

  public Media getCoverImage() {
    return coverImage;
  }

  public void setCoverImage(Media coverImage) {
    this.coverImage = coverImage;
  }

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

  public Set<CourseRegistration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(Set<CourseRegistration> registrations) {
    this.registrations = registrations;
  }
}
