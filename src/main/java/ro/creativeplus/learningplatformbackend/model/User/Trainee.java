package ro.creativeplus.learningplatformbackend.model.User;

import ro.creativeplus.learningplatformbackend.model.CourseRegistration;
import ro.creativeplus.learningplatformbackend.model.Project;
import ro.creativeplus.learningplatformbackend.model.QuizAttempt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Trainee extends User {
  private String country;

  @ManyToMany
  @JoinTable(
      name = "trainees_projects",
      joinColumns = @JoinColumn(name = "trainee_id"),
      inverseJoinColumns = @JoinColumn(name = "project_id"))
  private List<Project> projects = new ArrayList<>();

  @OneToMany(mappedBy = "trainee")
  Set<CourseRegistration> registrations;

  @OneToMany(mappedBy = "trainee")
  List<QuizAttempt> quizAttempts;

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

  public Set<CourseRegistration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(Set<CourseRegistration> registrations) {
    this.registrations = registrations;
  }

  public List<QuizAttempt> getQuizAttempts() {
    return quizAttempts;
  }

  public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
    this.quizAttempts = quizAttempts;
  }
}
