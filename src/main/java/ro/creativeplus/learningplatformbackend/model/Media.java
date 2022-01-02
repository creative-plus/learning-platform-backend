package ro.creativeplus.learningplatformbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Blob;

@Entity
public class Media {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", nullable = false)
  private String name;

  @NotEmpty
  @Column(name = "mime_type", nullable = false)
  private String mimeType;

  @NotEmpty
  @Lob
  @Column(name = "content", nullable = false)
  private byte[] content;

  public Media(String name, String mimeType, @NotEmpty byte[] content) {
    this.name = name;
    this.mimeType = mimeType;
    this.content = content;
  }

  public Media() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }
}
