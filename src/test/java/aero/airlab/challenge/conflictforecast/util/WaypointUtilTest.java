package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WaypointUtilTest {

  @InjectMocks
  WaypointUtil waypointUtil;

  Waypoint mockWaypoint = new Waypoint(101.355, 1.00355, 10001);

  @Test
  void shouldReturnGeoPoint() {
    GeoPoint geoPoint = waypointUtil.interpolateGeoPoint(mockWaypoint);

    assertEquals(geoPoint.getLon(), 101.355);
    assertEquals(geoPoint.getLat(), 1.00355);
  }
}