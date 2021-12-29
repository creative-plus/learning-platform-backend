package ro.creativeplus.learningplatformbackend.model.CourseSection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Learning extends CourseSection {
  @Lob
  @Column(name = "content", nullable = false)
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
