package ro.creativeplus.learningplatformbackend.model.CourseSection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Learning extends CourseSection {
  @Column(name = "content", nullable = false)
  private String content;

  public Learning() {
  }

  public Learning(int id, String content) {
    this.setId(id);
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
