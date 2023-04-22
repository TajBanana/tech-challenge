package aero.airlab.challenge.conflictforecast.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConflictRequestExceptionHandler {

  @ExceptionHandler(value = {MapperException.class, WayPointListException.class})
  public ResponseEntity<String> handleException(RuntimeException e) {
    return ResponseEntity.ok(e.getMessage());
  }
}
