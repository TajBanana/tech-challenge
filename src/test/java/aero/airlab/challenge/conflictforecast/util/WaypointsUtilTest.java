package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.exception.WayPointListException;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.util.geopoint.GeoPointUtil;
import aero.airlab.challenge.conflictforecast.util.waypoint.WaypointsUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class WaypointsUtilTest {

  @Mock
  private GeoPointUtil geoPointUtilMock;

  @InjectMocks
  WaypointsUtil wayPointsUtil;

  @Test
  public void testGetGeoPointAtCurrentTime() {

    List<Waypoint> referenceWaypointList = Arrays.asList(
        new Waypoint(1.0, 2.0, 100L),
        new Waypoint(3.0, 4.0, 200L),
        new Waypoint(5.0, 6.0, 300L)
    );
    long currentTime = 150L;
    int trajectoryId = 1;

    when(geoPointUtilMock.nextGeoPoint(referenceWaypointList.get(0), referenceWaypointList.get(1), currentTime))
        .thenReturn(new GeoPoint(2.0, 3.0));

    GeoPoint result = wayPointsUtil.getGeoPointAtCurrentTime(referenceWaypointList, currentTime, trajectoryId);
    Assertions.assertEquals(new GeoPoint(2.0, 3.0), result);
  }

  @Test
  public void testGetGeoPointAtExactCurrentTime() {

    List<Waypoint> referenceWaypointList = Arrays.asList(
        new Waypoint(1.0, 2.0, 100L),
        new Waypoint(3.0, 4.0, 200L),
        new Waypoint(5.0, 6.0, 300L)
    );
    long currentTime = 200L;
    int trajectoryId = 1;

    GeoPoint result = wayPointsUtil.getGeoPointAtCurrentTime(referenceWaypointList, currentTime, trajectoryId);
    Assertions.assertEquals(new GeoPoint(3.0, 4.0), result);
    verify(geoPointUtilMock, times(0)).nextGeoPoint(any(Waypoint.class), any(Waypoint.class), anyLong());

  }

  @Test
  public void testWayPointListEmpty() {

    List<Waypoint> referenceWaypointList = new ArrayList<>();
    long currentTime = 150L;
    int trajectoryId = 1;

    assertThrows(WayPointListException.class,
        () -> wayPointsUtil.getGeoPointAtCurrentTime(referenceWaypointList, currentTime, trajectoryId));
  }
}
