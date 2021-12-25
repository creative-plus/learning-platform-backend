package ro.creativeplus.learningplatformbackend.model;

import javax.persistence.Entity;

@Entity
public class Trainee extends User {
  private String country;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
