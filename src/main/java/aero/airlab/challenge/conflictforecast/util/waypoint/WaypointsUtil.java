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

  public GeoPoint getGeoPointAtCurrentTime(List<Waypoint> waypointList,
                                           long currentTime,
                                           int trajectoryId) {

    for (int j = 1; j < waypointList.size(); j++) {
      if (currentTime > waypointList.get(j).getTimestamp())
        continue;

      Waypoint waypoint = waypointList.get(j);

      if (currentTime == waypoint.getTimestamp()) {
        return new GeoPoint(waypoint.getLon(), waypoint.getLat());
      }
      else {
        Waypoint previousWaypoint = waypointList.get(j - 1);
        return geoPointUtil.nextGeoPoint(previousWaypoint, waypoint, currentTime);
      }
    }

    throw new WayPointListException(String.format("ID: %s | way point list is" +
        " null or empty", trajectoryId));
  }
}
