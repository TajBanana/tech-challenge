package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.util.time.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TimeUtilTest {

  @InjectMocks
  private TimeUtil timeUtil;
  private Trajectory trajectory1;
  private Trajectory trajectory2;

  @BeforeEach
  void setUp() {
    Waypoint waypoint1 = new Waypoint(0, 0, 1000L);
    Waypoint waypoint2 = new Waypoint(0, 0, 2000L);
    Waypoint waypoint3 = new Waypoint(0, 0, 3000L);
    List<Waypoint> waypoints1 = Arrays.asList(waypoint1, waypoint2);
    List<Waypoint> waypoints2 = Arrays.asList(waypoint2, waypoint3);
    trajectory1 = new Trajectory(1, waypoints1);
    trajectory2 = new Trajectory(2, waypoints2);
  }

  @Test
  void testGetMinTime() {
    List<Trajectory> trajectoryList = Arrays.asList(trajectory1, trajectory2);
    long minTime = timeUtil.getMinTime(trajectoryList);
    assertEquals(1000L, minTime);
  }

  @Test
  void testGetMaxTime() {
    List<Trajectory> trajectoryList = Arrays.asList(trajectory1, trajectory2);
    long maxTime = timeUtil.getMaxTime(trajectoryList);
    assertEquals(3000L, maxTime);
  }

  @Test
  void testGetWayPointsMaxTime() {
    long maxTime = timeUtil.getWayPointsMaxTime(trajectory1);
    assertEquals(2000L, maxTime);
  }

  @Test
  public void testTimeNotWithinRangeOfWaypointsWhenTimeIsAfterLastWaypoint() {
    List<Waypoint> waypoints = new ArrayList<>();
    waypoints.add(new Waypoint(10.0, 20.0, 1000L));
    waypoints.add(new Waypoint(20.0, 30.0, 2000L));
    long currentTime = 3000L;
    assertTrue(timeUtil.timeNotWithinRangeOfWaypoints(currentTime, waypoints));
  }

  @Test
  public void testTimeNotWithinRangeOfWaypointsWhenTimeIsWithinWaypointsRange() {
    List<Waypoint> waypoints = new ArrayList<>();
    waypoints.add(new Waypoint(10.0, 20.0, 1000L));
    waypoints.add(new Waypoint(20.0, 30.0, 2000L));
    long currentTime = 1500L;
    assertFalse(timeUtil.timeNotWithinRangeOfWaypoints(currentTime, waypoints));
  }
}