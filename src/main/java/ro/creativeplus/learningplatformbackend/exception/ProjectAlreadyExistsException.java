package ro.creativeplus.learningplatformbackend.exception;

public class ProjectAlreadyExistsException extends RuntimeException {
  public ProjectAlreadyExistsException() {
    super("There is already a project with this ID.");
  }
}
