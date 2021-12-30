package ro.creativeplus.learningplatformbackend.exception;

public class AuthException extends RuntimeException {
  public AuthException(String message){
    super(message);
  }
}
