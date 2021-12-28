package ro.creativeplus.learningplatformbackend.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Trainee extends User {
  private String country;

  @ManyToMany(mappedBy = "trainees")
  private List<Project> projects = new ArrayList<>();

  @OneToMany(mappedBy = "trainee")
  Set<CourseRegistration> registrations;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }
}
