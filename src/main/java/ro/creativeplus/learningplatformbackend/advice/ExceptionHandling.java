package ro.creativeplus.learningplatformbackend.advice;

import org.springframework.validation.FieldError;
import ro.creativeplus.learningplatformbackend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandling {
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Object> dtoValidation(MethodArgumentNotValidException exception){
    Map<String, Object> body = new HashMap<>();
    body.put("message", "Please correct the following errors.");
    body.put("code", exception.getClass().getSimpleName());
    List<FieldError> fieldErrors = exception.getFieldErrors();
    List<?> fieldErrorMaps = fieldErrors.stream()
        .map(err -> {
          Map<String, String> errorObject = new HashMap<>();
          errorObject.put(err.getField(), err.getDefaultMessage());
          return errorObject;
        })
        .collect(Collectors.toList());
    if(!fieldErrorMaps.isEmpty()) {
      body.put("fieldErrors", fieldErrorMaps);
    }
    List<ObjectError> objectErrors = exception.getGlobalErrors();
    List<?> objectErrorMaps = objectErrors.stream()
        .map(err -> {
          Map<String, String> errorObject = new HashMap<>();
          errorObject.put(err.getObjectName(), err.getDefaultMessage());
          return errorObject;
        })
        .collect(Collectors.toList());
    if(!objectErrorMaps.isEmpty()) {
      body.put("objectErrors", objectErrorMaps);
    }
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ObjectNotFoundException.class})
  public ResponseEntity<Object> notFound(ObjectNotFoundException exception){
    Map<String, Object> body = new HashMap<>();
    body.put("message", exception.getMessage());
    body.put("code", exception.getClass().getSimpleName());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({ObjectAlreadyExistsException.class, NotAllowedException.class})
  public ResponseEntity<Object> notAllowedExceptions(RuntimeException exception){
    Map<String, Object> body = new HashMap<>();
    body.put("message", exception.getMessage());
    body.put("code", exception.getClass().getSimpleName());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({AuthException.class, ForbiddenException.class})
  public ResponseEntity<Object> authForbiddenExceptions(RuntimeException exception){
    Map<String, Object> body = new HashMap<>();
    body.put("message", exception.getMessage());
    body.put("code", exception.getClass().getSimpleName());
    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({WrongQuizAnswerException.class})
  public ResponseEntity<Object> wrongQuizAnswers(WrongQuizAnswerException exception){
    Map<String, Object> body = new HashMap<>();
    body.put("message", exception.getMessage());
    body.put("code", exception.getClass().getSimpleName());
    body.put("remainingAttempts", exception.getRemainingAttempts());
    if(exception.isShowCorrectQuestionAnswers()) {
      body.put("correctQuestionAnswers", exception.getCorrectQuestionAnswers());
    }
    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({InternalErrorException.class})
  public ResponseEntity<Object> internalErrors(InternalErrorException exception){
    Map<String, Object> body = new HashMap<>();
    body.put("message", exception.getMessage() + " Please contact the system admin.");
    body.put("code", exception.getClass().getSimpleName());
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}