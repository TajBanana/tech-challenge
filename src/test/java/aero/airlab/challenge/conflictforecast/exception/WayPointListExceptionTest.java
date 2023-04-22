package aero.airlab.challenge.conflictforecast.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class WayPointListExceptionTest {

  @Test
  public void testMapperExceptionWithNonNullMessage() {
    String message = "This is a test message";
    WayPointListException exception = new WayPointListException(message);
    assertThrows(WayPointListException.class, () -> {
      throw exception;
    });
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testMapperExceptionWithNullMessage() {
    WayPointListException exception = new WayPointListException(null);
    assertThrows(WayPointListException.class, () -> {
      throw exception;
    });
    assertNull(exception.getMessage());
  }

  @Test
  public void testMapperExceptionWithEmptyMessage() {
    String message = "";
    WayPointListException exception = new WayPointListException(message);
    assertThrows(WayPointListException.class, () -> {
      throw exception;
    });
    assertEquals(message, exception.getMessage());
  }

}