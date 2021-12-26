package ro.creativeplus.learningplatformbackend.dto.project;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

public class ProjectRequestDto {

  @NotEmpty
  private String name;

  private String description;

  @NotEmpty
  private Date startDate;

  @NotEmpty
  private Date endDate;

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
