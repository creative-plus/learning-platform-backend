package ro.creativeplus.learningplatformbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Organization {
  @Id
  @SequenceGenerator(name = "generator", sequenceName = "ORGANIZATION_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
  private int id;

  @Column()
  @NotEmpty
  private String name;

  @Column
  private String type;

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
