package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeodeticCalc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeoPointUtilTest {

  @Mock
  private WaypointUtil waypointUtil;

  @InjectMocks
  GeoPointUtil geoPointUtil;

  GeodeticCalc geodeticCalc = GeodeticCalc.Companion.geodeticCalcWSSS();
  DecimalFormat decimalFormat = new DecimalFormat("#.###");

  Waypoint mockWayPoint = new Waypoint(101, 1.01, 0);
  Waypoint mockNextWayPoint = new Waypoint(102, 1.11, 10000);
  GeoPoint mockPreviousGeoPoint = new GeoPoint(101, 1.01);
  GeoPoint mockNextGeoPoint = new GeoPoint(102, 1.11);
  double totalDistance = geodeticCalc.distance(mockPreviousGeoPoint,
      mockNextGeoPoint);

  @Test
  void shouldReturnHalfTotalDistance() {
    when(waypointUtil.interpolateGeoPoint(mockWayPoint)).thenReturn(mockPreviousGeoPoint);
    when(waypointUtil.interpolateGeoPoint(mockNextWayPoint)).thenReturn(mockNextGeoPoint);

    GeoPoint geoPoint = geoPointUtil.nextGeoPoint(mockWayPoint, mockNextWayPoint, 5000);
    double roundedGeoPointDistance = Double.parseDouble(decimalFormat.format(geodeticCalc.distance(mockPreviousGeoPoint,
        geoPoint)));

    assertEquals(Double.parseDouble(decimalFormat.format(totalDistance / 2)), roundedGeoPointDistance);
  }

  @Test
  void shouldReturnQuarterTotalDistance() {
    when(waypointUtil.interpolateGeoPoint(mockWayPoint)).thenReturn(mockPreviousGeoPoint);
    when(waypointUtil.interpolateGeoPoint(mockNextWayPoint)).thenReturn(mockNextGeoPoint);

    GeoPoint geoPoint = geoPointUtil.nextGeoPoint(mockWayPoint,
        mockNextWayPoint, 2500);
    double roundedGeoPointDistance = Double.parseDouble(decimalFormat.format(geodeticCalc.distance(mockPreviousGeoPoint,
        geoPoint)));

    assertEquals(roundedGeoPointDistance,
        Double.parseDouble(decimalFormat.format(totalDistance / 4)));
  }

  @Test
  void shouldReturn3QuarterTotalDistance() {
    when(waypointUtil.interpolateGeoPoint(mockWayPoint)).thenReturn(mockPreviousGeoPoint);
    when(waypointUtil.interpolateGeoPoint(mockNextWayPoint)).thenReturn(mockNextGeoPoint);

    GeoPoint geoPoint = geoPointUtil.nextGeoPoint(mockWayPoint,
        mockNextWayPoint, 7500);
    double roundedGeoPointDistance = Double.parseDouble(decimalFormat.format(geodeticCalc.distance(mockPreviousGeoPoint,
        geoPoint)));

    assertEquals(roundedGeoPointDistance,
        Double.parseDouble(decimalFormat.format(totalDistance / 4 * 3)));
  }
}