package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.exception.WayPointListException;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReferenceWayPointUtil {

  private final GeoPointUtil geoPointUtil;

  public ReferenceWayPointUtil(GeoPointUtil geoPointUtil) {
    this.geoPointUtil = geoPointUtil;
  }

  public GeoPoint getGeoPointAtCurrentTime(List<Waypoint> referenceWaypointList,
                                           long currentTime, int trajectoryId) {

    for (int j = 1; j < referenceWaypointList.size(); j++) {
      System.out.println(currentTime);
      System.out.println(referenceWaypointList.get(j).getTimestamp());

      if (currentTime > referenceWaypointList.get(j).getTimestamp())
        continue;

      Waypoint waypoint = referenceWaypointList.get(j);

      if (currentTime == waypoint.getTimestamp()) {
        //System.out.println(trajectoryId);
        System.out.println("if loop");
        return new GeoPoint(waypoint.getLon(), waypoint.getLat());
      }
      else {
        Waypoint previousWaypoint = referenceWaypointList.get(j - 1);

        return geoPointUtil.nextGeoPoint(previousWaypoint, waypoint, currentTime);
      }
    }

    throw new WayPointListException("way point list is null or empty");
  }
}
