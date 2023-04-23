package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.exception.WayPointListException;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReferenceWayPointUtil {

  private final GeoPointUtil geoPointUtil;
  private final TimeUtil timeUtil;

  public ReferenceWayPointUtil(GeoPointUtil geoPointUtil, TimeUtil timeUtil) {
    this.geoPointUtil = geoPointUtil;
    this.timeUtil = timeUtil;
  }

  public GeoPoint getGeoPointAtCurrentTime(List<Waypoint> waypointList,
                                           long currentTime,
                                           int trajectoryId) {
    //System.out.println("from util");
    //System.out.println(currentTime < waypointList.get(0).getTimestamp() ||
    //    currentTime > waypointList.get(waypointList.size() - 1)
    //                                       .getTimestamp());

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


    System.out.println("min time: " + timeUtil.debugUtilMinTime(waypointList));
    System.out.println("max time: " + timeUtil.debugUtilMaxTime(waypointList));
    System.out.println(currentTime);
    System.out.println(waypointList);
    throw new WayPointListException(String.format("ID: %s | way point list is" +
        " null or empty", trajectoryId));
  }
}
