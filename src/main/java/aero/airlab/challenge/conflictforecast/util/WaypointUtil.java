package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import org.springframework.stereotype.Component;

@Component
public class WaypointUtil {
  public GeoPoint toGeoPoint(Waypoint waypoint) {
    return new GeoPoint(waypoint.getLon(), waypoint.getLat());
  }
}
