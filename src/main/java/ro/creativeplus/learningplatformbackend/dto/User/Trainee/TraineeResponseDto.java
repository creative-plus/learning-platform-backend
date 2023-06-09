package ro.creativeplus.learningplatformbackend.dto.User.Trainee;


import ro.creativeplus.learningplatformbackend.dto.project.ProjectResponseDto;

import java.util.Date;
import java.util.List;

public class TraineeResponseDto {
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String country;
  private String phoneNumber;
  private Date birthDate;
  private Integer organizationId;
  private List<ProjectResponseDto> projects;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public List<ProjectResponseDto> getProjects() {
    return projects;
  }

  public void setProjects(List<ProjectResponseDto> projects) {
    this.projects = projects;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public Integer getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Integer organizationId) {
    this.organizationId = organizationId;
  }
}
