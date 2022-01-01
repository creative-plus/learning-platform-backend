package ro.creativeplus.learningplatformbackend.exception;

public class NotAllowedException extends RuntimeException {
  public NotAllowedException(String message) {
    super(message);
  }
}
