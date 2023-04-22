package aero.airlab.challenge.conflictforecast.service;

import aero.airlab.challenge.conflictforecast.api.*;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.util.GeoPointUtil;
import aero.airlab.challenge.conflictforecast.util.WaypointUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictResponseService {
  private long minTime;
  private long maxTime;

  private final GeoPointUtil geoPointUtil;

  public ConflictResponseService(GeoPointUtil geoPointUtil) {
    this.geoPointUtil = geoPointUtil;
  }

  public ConflictForecastResponse get(ConflictForecastRequest request) {
    List<Trajectory> trajectoryList = request.component1();
    List<SeparationRequirement> separationRequirements = request.component2();
    List<Conflict> conflictList = new ArrayList<>();

    getMinMaxTime(trajectoryList);

    long currentTime = minTime;
    while (currentTime <= maxTime) {
      System.out.println("current time: " + currentTime);

      for (int i = 0; i < trajectoryList.size(); i++) { //loop all trajectories
        Trajectory trajectory = trajectoryList.get(i);
        List<Waypoint> referenceWaypointList = trajectory.getWaypoints();
        int referenceId = trajectory.getId();
        GeoPoint referenceGeoPoint = null;

        //skip if time is not within range
        if (currentTime < referenceWaypointList.get(0).getTimestamp() ||
            currentTime > referenceWaypointList.get(referenceWaypointList.size() - 1)
                                               .getTimestamp())
          continue;

        // get current reference geopoint
        for (int j = 0; j < referenceWaypointList.size(); j++) {
          if (currentTime < referenceWaypointList.get(j).getTimestamp())
            continue;

          Waypoint waypoint = referenceWaypointList.get(j);

          if (currentTime == waypoint.getTimestamp()) {
            referenceGeoPoint = new GeoPoint(waypoint.getLon(), waypoint.getLat());
            System.out.println(referenceId);
            break;
          }
          else if (currentTime != waypoint.getTimestamp()) {
            Waypoint nextWaypoint = referenceWaypointList.get(j + 1);
            referenceGeoPoint = geoPointUtil.nextGeoPoint(waypoint, nextWaypoint, currentTime);
            System.out.println(referenceId);
            break;
          }
        }

/*
        for (int k = 0; k < trajectoryList.size(); k++) { //loop all trajectories and compare
          if (k == i) continue; //skip for self


        }
*/
      }

      currentTime += 5000;
    }

    System.out.println("loop end");

    //TODO return conflict response
    return null;
  }

  private void getSeparationRequirement(List<SeparationRequirement> separationRequirements) {
    System.out.println(separationRequirements);
  }

  private void getMinMaxTime(List<Trajectory> trajectoryList) {
    this.minTime = Long.MAX_VALUE;
    this.maxTime = Long.MIN_VALUE;

    for (Trajectory trajectory : trajectoryList) {
      List<Waypoint> waypoints = trajectory.component2();

      for (Waypoint waypoint : waypoints) {
        long time = waypoint.component3();
        this.minTime = Math.min(minTime, time);
        this.maxTime = Math.max(maxTime, time);
      }
    }
    System.out.println("MIN: " + minTime);
    System.out.println("MAX: " + maxTime);

  }


}
