package ro.creativeplus.learningplatformbackend.dto.User.Trainee;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TraineeRequestDto {
  @NotEmpty
  private String firstName;

  @NotEmpty
  private String lastName;

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String country;

  @Length(min = 8, max = 14)
  private String phoneNumber;

  @NotEmpty
  private List<@NotNull Integer> projectIds;

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

  public List<Integer> getProjectIds() {
    return projectIds;
  }

  public void setProjectIds(List<Integer> projectIds) {
    this.projectIds = projectIds;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
