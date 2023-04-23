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
class ConflictResponseUtilTest {

  @Autowired
  private ConflictResponseUtil conflictResponseUtil;

  @Test
  public void testGetConflictWithMockInputs() {
    List<Waypoint> waypointsA = new ArrayList<>(
        List.of(
            new Waypoint(0.0, 0.0, 10_000L),
            new Waypoint(0.0001, 0.0001, 15_000L),
            new Waypoint(0.0002, 0.0002, 20_000L),
            new Waypoint(0.0003, 0.0003, 25_000L),
            new Waypoint(0.0004, 0.0004, 30_000L),
            new Waypoint(0.0005, 0.0005, 35_000L),
            new Waypoint(0.0006, 0.0006, 40_000L)
        )
    );
    Trajectory trajectoryA = new Trajectory(12345, waypointsA);

    List<Waypoint> waypointsB = new ArrayList<>(
        List.of(
            new Waypoint(0.00030, 0.00030, 24_500L),
            new Waypoint(0.00080, 0.00080, 32_500L),
            new Waypoint(0.00150, 0.00150, 37_500L),
            new Waypoint(0.00260, 0.00260, 42_500L),
            new Waypoint(0.00350, 0.00350, 47_500L),
            new Waypoint(0.00400, 0.00400, 52_500L)
        )
    );
    Trajectory trajectoryB = new Trajectory(54321, waypointsB);

    List<Trajectory> trajectoryList = new ArrayList<>(
        List.of(
            trajectoryA,
            trajectoryB
        )
    );

    GeoPoint referenceGeopoint = new GeoPoint(0.0003, 0.0003);

    SeparationRequirement separationRequirement =
        new SeparationRequirement(new GeoPoint(0.0, 0.0), 5000.0, 3000.0);


    List<Conflict> conflicts = conflictResponseUtil.getConflicts(trajectoryList,
        List.of(separationRequirement),
        0, referenceGeopoint, trajectoryA, 25000);

    Conflict expectedConflict = new Conflict(
        12345,
        new TemporalGeoPoint(3.0E-4, 3.0E-4, 25000),
        new TemporalGeoPoint(6.0E-4, 6.0E-4, 40000),
        54321,
        new TemporalGeoPoint(3.312499999978861E-4, 3.312500000010947E-4, 25000),
        new TemporalGeoPoint(0.002049999999812418, 0.002050000000096983, 40000)
    );

    assertEquals(List.of(expectedConflict), conflicts);


  }
}