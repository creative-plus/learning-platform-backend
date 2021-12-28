package ro.creativeplus.learningplatformbackend.exception;

public class ProjectNotFoundException extends ObjectNotFoundException {
  public ProjectNotFoundException() {
    super("Project was not found.");
  }
}
