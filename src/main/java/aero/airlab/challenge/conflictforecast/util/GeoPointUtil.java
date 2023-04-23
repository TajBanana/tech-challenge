package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeodeticCalc;
import kotlin.Pair;
import org.springframework.stereotype.Component;

@Component
public class GeoPointUtil {

  private final WaypointUtil waypointUtil;
  private final GeodeticCalc geodeticCalc = GeodeticCalc.Companion.geodeticCalcWSSS();

  public GeoPointUtil(WaypointUtil waypointUtil) {
    this.waypointUtil = waypointUtil;
  }

  public GeoPoint nextGeoPoint(Waypoint waypoint, Waypoint nextWaypoint,
                               long currentTime) {
    GeoPoint previousGeoPoint = waypointUtil.interpolateGeoPoint(waypoint);
    GeoPoint nextGeoPoint = waypointUtil.interpolateGeoPoint(nextWaypoint);
    Pair<Double, Double> headingAndDistance = geodeticCalc
        .headingAndDistanceTo(previousGeoPoint, nextGeoPoint);
    Double heading = headingAndDistance.component1();
    Double distance = headingAndDistance.component2();
    double interpolateDistance =
        distance / (nextWaypoint.getTimestamp() - waypoint.getTimestamp()) * (currentTime - waypoint.getTimestamp());

    return geodeticCalc.nextPointFrom(previousGeoPoint, heading,
        interpolateDistance);
  }
}
