package aero.airlab.challenge.conflictforecast.util.waypoint;

import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.exception.WayPointListException;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.util.geopoint.GeoPointUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WaypointsUtil {

  private final GeoPointUtil geoPointUtil;

  public WaypointsUtil(GeoPointUtil geoPointUtil) {
    this.geoPointUtil = geoPointUtil;
  }

  public GeoPoint getGeoPointAtCurrentTime(List<Waypoint> waypoints,
                                           long currentTime,
                                           int trajectoryId) {

    if ( waypoints.size() != 0 && (currentTime < waypoints.get(0).getTimestamp() ||
        currentTime > waypoints.get(waypoints.size() - 1).getTimestamp()))
      throw new WayPointListException(String.format("ID: %s | Time is not within range of waypoints", trajectoryId));


    for (int j = 1; j < waypoints.size(); j++) {
      if (currentTime > waypoints.get(j).getTimestamp())
        continue;

      Waypoint waypoint = waypoints.get(j);

      if (currentTime == waypoint.getTimestamp()) {
        return new GeoPoint(waypoint.getLon(), waypoint.getLat());
      }
      else {
        Waypoint previousWaypoint = waypoints.get(j - 1);
        return geoPointUtil.nextGeoPoint(previousWaypoint, waypoint, currentTime);
      }
    }

    throw new WayPointListException(String.format("ID: %s | way point list is" +
        " null or empty", trajectoryId));
  }
}
