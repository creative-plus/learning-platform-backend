package ro.creativeplus.learningplatformbackend.model;

import ro.creativeplus.learningplatformbackend.model.User.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

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

  @OneToMany(mappedBy = "organization", orphanRemoval = true)
  private List<User> users = new ArrayList<>();

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
