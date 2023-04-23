package aero.airlab.challenge.conflictforecast.service;

import aero.airlab.challenge.conflictforecast.api.*;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeodeticCalc;
import aero.airlab.challenge.conflictforecast.util.ConflictUtil;
import aero.airlab.challenge.conflictforecast.util.ReferenceWayPointUtil;
import aero.airlab.challenge.conflictforecast.util.SeparationRequirementUtil;
import aero.airlab.challenge.conflictforecast.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictResponseService {

  private final ReferenceWayPointUtil wayPointUtil;
  private final TimeUtil timeUtil;
  private final SeparationRequirementUtil separationRequirementUtil;
  private final ConflictUtil conflictUtil;
  private final GeodeticCalc geodeticCalc = GeodeticCalc.Companion.geodeticCalcWSSS();

  public ConflictResponseService(ReferenceWayPointUtil referenceWayPointUtil, TimeUtil timeUtil, SeparationRequirementUtil separationRequirementUtil, ConflictUtil conflictUtil) {
    this.wayPointUtil = referenceWayPointUtil;
    this.timeUtil = timeUtil;
    this.separationRequirementUtil = separationRequirementUtil;
    this.conflictUtil = conflictUtil;
  }

  public ConflictForecastResponse check(ConflictForecastRequest request) {
    List<Trajectory> trajectoryList = request.component1();
    List<SeparationRequirement> separationRequirements = request.component2();
    List<Conflict> conflictList = new ArrayList<>();

    long minTime = timeUtil.getMinTime(trajectoryList);
    long maxTime = timeUtil.getMaxTime(trajectoryList);

    long currentTime = minTime;
    while (currentTime <= maxTime) {
      //System.out.println("current time: " + currentTime);

      for (int i = 0; i < trajectoryList.size(); i++) { //loop all trajectories
        Trajectory referenceTrajectory = trajectoryList.get(i);
        List<Waypoint> referenceWaypointList = referenceTrajectory.getWaypoints();
        int referenceId = referenceTrajectory.getId();

        //skip if time is not within range
        if (currentTime < referenceWaypointList.get(0).getTimestamp() ||
            currentTime > referenceWaypointList.get(referenceWaypointList.size() - 1)
                                               .getTimestamp())
          continue;

        // get current reference geopoint
        //System.out.println("reference geopoint");
        GeoPoint referenceGeoPoint =
            wayPointUtil.getGeoPointAtCurrentTime(referenceWaypointList,
                currentTime, referenceId);

        for (int k = 0; k < trajectoryList.size(); k++) { //loop all trajectories and compare
          if (k == i) continue; //skip for self
          Trajectory comparisonTrajectory = trajectoryList.get(k);
          List<Waypoint> comparisonWaypointList =
              comparisonTrajectory.getWaypoints();
          int comparisonId = comparisonTrajectory.getId();

          //System.out.println("from service");
          //System.out.println(currentTime < comparisonWaypointList.get(0).getTimestamp() ||
          //    currentTime > comparisonWaypointList.get(comparisonWaypointList.size() - 1)
          //                                        .getTimestamp());

          if (currentTime < comparisonWaypointList.get(0).getTimestamp() ||
              currentTime > comparisonWaypointList.get(comparisonWaypointList.size() - 1)
                                                 .getTimestamp()) continue;

          //System.out.println("comparison geopoint");
          GeoPoint comparisonGeopoint =
              wayPointUtil.getGeoPointAtCurrentTime(comparisonWaypointList,
                  currentTime, comparisonId);

          double separationRequirement =
              separationRequirementUtil.lateralSeparation(separationRequirements, referenceGeoPoint, comparisonGeopoint);

          if (geodeticCalc.distance(referenceGeoPoint, comparisonGeopoint) < separationRequirement) {
            Conflict conflict = conflictUtil.getConflict(currentTime, referenceTrajectory,
                comparisonTrajectory, separationRequirements);
            conflictList.add(conflict);
          }
        }
      }
      currentTime += 5000;
    }

    //TODO return conflict response
    return new ConflictForecastResponse(conflictList);
  }
}
