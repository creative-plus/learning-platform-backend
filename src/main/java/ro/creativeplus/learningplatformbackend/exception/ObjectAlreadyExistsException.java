package ro.creativeplus.learningplatformbackend.exception;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String message){
        super(message);
    }
}
