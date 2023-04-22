package aero.airlab.challenge.conflictforecast.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConflictRequestExceptionHandlerTest {

  MapperException mapperException = new MapperException("MapperException message");

  WayPointListException wayPointListException = new WayPointListException("WayPointListException message");

  @InjectMocks
  ConflictRequestExceptionHandler conflictRequestExceptionHandler;

  @Test
  void handleMapperException() {
    ResponseEntity<String> responseEntity = conflictRequestExceptionHandler.handleException(mapperException);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("MapperException message", responseEntity.getBody());
  }

  @Test
  void handleWayPointListException() {
    ResponseEntity<String> responseEntity =
        conflictRequestExceptionHandler.handleException(wayPointListException);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("WayPointListException message", responseEntity.getBody());
  }

}