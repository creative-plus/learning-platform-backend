package ro.creativeplus.learningplatformbackend.exception;

public class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException() {
    super("Project was not found.");
  }
}
