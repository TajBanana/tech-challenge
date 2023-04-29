package aero.airlab.challenge.conflictforecast.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesTest {

  @Test
  void testGetConflictStartTime() {
    long expectedConflictStartTime = 123456789L;
    Properties properties = Properties.builder().conflictStartTime(expectedConflictStartTime).build();
    assertEquals(expectedConflictStartTime, properties.getConflictStartTime());
  }

  @Test
  void testGetTrajectories() {
    String expectedTrajectories = "ABCDEF";
    Properties properties = Properties.builder().trajectories(expectedTrajectories).build();
    assertEquals(expectedTrajectories, properties.getTrajectories());
  }

  @Test
  void testGetters() {
    long expectedConflictStartTime = 123456789L;
    String expectedTrajectories = "ABCDEF";
    Properties properties = Properties.builder().conflictStartTime(expectedConflictStartTime).trajectories(expectedTrajectories).build();
    assertEquals(expectedConflictStartTime, properties.getConflictStartTime());
    assertEquals(expectedTrajectories, properties.getTrajectories());
  }

}