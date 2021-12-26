package ro.creativeplus.learningplatformbackend.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trainee extends User {
  private String country;

  @ManyToMany(mappedBy = "trainees")
  private List<Project> projects = new ArrayList<>();

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
