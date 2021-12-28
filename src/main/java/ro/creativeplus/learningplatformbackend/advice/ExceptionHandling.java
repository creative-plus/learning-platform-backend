package ro.creativeplus.learningplatformbackend.advice;

import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.creativeplus.learningplatformbackend.utils.ErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandling {
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Object> dtoValidation(MethodArgumentNotValidException exception){
    Map<String,Object> body = new HashMap<>();
    List<ObjectError> errors = exception.getAllErrors();
    List<ErrorResponse> bodyResponse = errors.stream()
        .map(err -> new ErrorResponse(err.getDefaultMessage()))
        .collect(Collectors.toList());
    body.put("timestamp", LocalDateTime.now());
    body.put("errors", bodyResponse);
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ObjectNotFoundException.class})
  public ResponseEntity<Object> notFound(ObjectNotFoundException exception){
    Map<String,Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("errors", exception.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
}