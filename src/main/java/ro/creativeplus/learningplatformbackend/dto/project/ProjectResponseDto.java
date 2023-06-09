package ro.creativeplus.learningplatformbackend.dto.project;

import java.sql.Date;

public class ProjectResponseDto {
  private int id;
  private String name;
  private String description;
  private Date startDate;
  private Date endDate;
  private Integer totalBudget;
  private String financeMean;
  private Integer minAge;
  private Integer maxAge;

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
}
