package ro.creativeplus.learningplatformbackend.model;

import ro.creativeplus.learningplatformbackend.model.User.Trainee;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
public class Project {
  @Id
  @SequenceGenerator(name = "generator", sequenceName = "PROJECT_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
  private int id;

  @Column()
  @NotEmpty
  private String name;

  @Column()
  private String description;

  @Column
  @NotNull
  private Date startDate;

  @Column
  @NotNull
  private Date endDate;

  @Column
  private Integer totalBudget;

  @Column
  private String financeMean;

  @Column
  private Integer minAge;

  @Column
  private Integer maxAge;

  @ManyToMany(mappedBy = "projects")
  private List<Trainee> trainees = new ArrayList<>();

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

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public List<Trainee> getTrainees() {
    return trainees;
  }

  public void setTrainees(List<Trainee> trainees) {
    this.trainees = trainees;
  }

  public Integer getTotalBudget() {
    return totalBudget;
  }

  public void setTotalBudget(Integer totalBudget) {
    this.totalBudget = totalBudget;
  }

  public String getFinanceMean() {
    return financeMean;
  }

  public void setFinanceMean(String financeMean) {
    this.financeMean = financeMean;
  }

  public Integer getMinAge() {
    return minAge;
  }

  public void setMinAge(Integer minAge) {
    this.minAge = minAge;
  }

  public Integer getMaxAge() {
    return maxAge;
  }

  public void setMaxAge(Integer maxAge) {
    this.maxAge = maxAge;
  }
}
