package ro.creativeplus.learningplatformbackend.exception;

public class EmailNotSentException extends InternalErrorException {
  public EmailNotSentException(String message) { super(message); }
}
