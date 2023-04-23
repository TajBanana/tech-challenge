package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Conflict;
import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.TemporalGeoPoint;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConflictUtilTest {

  @Autowired
  private ConflictUtil conflictUtil;

  @Test
  public void shouldReturnEntirePathConflict() {
    List<SeparationRequirement> separationRequirements =
        new ArrayList<>(List.of(
            new SeparationRequirement(
                new GeoPoint(0.0, 0.0), 5000.0, 5000.0),
            new SeparationRequirement(
                new GeoPoint(0.0, 0.0), 10000.0, 10000.0)
        ));


    List<Waypoint> waypointsA = new ArrayList<>(List.of(
        new Waypoint(0.0, 0.0, 5000),
        new Waypoint(0.1, 0.1, 15000),
        new Waypoint(0.2, 0.2, 25000)
    ));

    List<Waypoint> waypointsB = new ArrayList<>(List.of(
        new Waypoint(0.0, 0.0, 5000),
        new Waypoint(0.1, 0.1, 15000),
        new Waypoint(0.2, 0.2, 25000)

    ));

    Trajectory trajectoryA = new Trajectory(123, waypointsA);
    Trajectory trajectoryB = new Trajectory(321, waypointsB);

    Conflict conflict = conflictUtil.getConflict(5000, trajectoryA, trajectoryB,
        separationRequirements);

    Conflict expectedConflict = new Conflict(123, new TemporalGeoPoint(0.0, 0.0
        , 5000), new TemporalGeoPoint(0.2, 0.2, 25000), 321,
        new TemporalGeoPoint(0.0, 0.0
        , 5000), new TemporalGeoPoint(0.2, 0.2, 25000));

    assertEquals(expectedConflict, conflict);
  }

  @Test
  public void shouldReturnPartialPathConflict() {
    List<SeparationRequirement> separationRequirements =
        new ArrayList<>(List.of(
            new SeparationRequirement(
                new GeoPoint(0.0, 0.0), 5000.0, 5000.0),
            new SeparationRequirement(
                new GeoPoint(0.0, 0.0), 10000.0, 10000.0)
        ));


    List<Waypoint> waypointsA = new ArrayList<>(List.of(
        new Waypoint(0.0, 0.0, 5000),
        new Waypoint(0.1, 0.1, 10000),
        new Waypoint(0.2, 0.2, 15000),
        new Waypoint(0.3, 0.3, 20000),
        new Waypoint(0.4, 0.4, 25000)
    ));

    List<Waypoint> waypointsB = new ArrayList<>(List.of(
        new Waypoint(0.0, 0.0, 5000),
        new Waypoint(0.15, 0.15, 10000),
        new Waypoint(0.3, 0.3, 15000),
        new Waypoint(0.45, 0.45, 20000),
        new Waypoint(0.6, 0.6, 25000)

    ));

    Trajectory trajectoryA = new Trajectory(123, waypointsA);
    Trajectory trajectoryB = new Trajectory(321, waypointsB);

    Conflict conflict = conflictUtil.getConflict(5000, trajectoryA,
        trajectoryB,
        separationRequirements);

    System.out.println(conflict);

    Conflict expectedConflict = new Conflict(123, new TemporalGeoPoint(0.0, 0.0
        , 5000), new TemporalGeoPoint(0.2, 0.2, 15000), 321,
        new TemporalGeoPoint(0.0, 0.0
            , 5000), new TemporalGeoPoint(0.3, 0.3, 15000));

    assertEquals(expectedConflict, conflict);
  }

  @Test
  public void shouldReturnSinglePathConflict() {
    List<SeparationRequirement> separationRequirements =
        new ArrayList<>(List.of(
            new SeparationRequirement(
                new GeoPoint(0.0, 0.0), 5000.0, 5000.0),
            new SeparationRequirement(
                new GeoPoint(0.0, 0.0), 10000.0, 10000.0)
        ));


    List<Waypoint> waypointsA = new ArrayList<>(List.of(
        new Waypoint(0.0, 0.0, 5000),
        new Waypoint(0.1, 0.1, 10000),
        new Waypoint(0.2, 0.2, 15000),
        new Waypoint(0.3, 0.3, 20000),
        new Waypoint(0.4, 0.4, 25000)
    ));

    List<Waypoint> waypointsB = new ArrayList<>(List.of(
        new Waypoint(10.0, 10.0, 5000),
        new Waypoint(5.0, 5.0, 10000),
        new Waypoint(0.2, 0.2, 15000),
        new Waypoint(1.0, 1.0, 20000),
        new Waypoint(1.1, 1.1, 25000)
    ));

    Trajectory trajectoryA = new Trajectory(123, waypointsA);
    Trajectory trajectoryB = new Trajectory(321, waypointsB);

    Conflict conflict = conflictUtil.getConflict(15000, trajectoryA,
        trajectoryB,
        separationRequirements);

    System.out.println(conflict);

    Conflict expectedConflict = new Conflict(123, new TemporalGeoPoint(0.2, 0.2
        , 15000), new TemporalGeoPoint(0.3, 0.3, 20000), 321,
        new TemporalGeoPoint(0.2, 0.2
            , 15000), new TemporalGeoPoint(1.0, 1.0, 20000));

    assertEquals(expectedConflict, conflict);
  }
}