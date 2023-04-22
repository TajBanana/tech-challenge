package aero.airlab.challenge.conflictforecast.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperExceptionTest {

  @Test
  public void testMapperExceptionWithNonNullMessage() {
    String message = "This is a test message";
    MapperException exception = new MapperException(message);
    assertThrows(MapperException.class, () -> {
      throw exception;
    });
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testMapperExceptionWithNullMessage() {
    MapperException exception = new MapperException(null);
    assertThrows(MapperException.class, () -> {
      throw exception;
    });
    assertNull(exception.getMessage());
  }

  @Test
  public void testMapperExceptionWithEmptyMessage() {
    String message = "";
    MapperException exception = new MapperException(message);
    assertThrows(MapperException.class, () -> {
      throw exception;
    });
    assertEquals(message, exception.getMessage());
  }
}