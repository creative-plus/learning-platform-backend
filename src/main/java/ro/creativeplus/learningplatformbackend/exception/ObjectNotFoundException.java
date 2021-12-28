package ro.creativeplus.learningplatformbackend.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message){
        super(message);
    }
}
