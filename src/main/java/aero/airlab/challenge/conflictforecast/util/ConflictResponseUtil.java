package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Conflict;
import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeodeticCalc;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConflictResponseUtil {
  private final WaypointsUtil waypointsUtil;
  private final TimeUtil timeUtil;
  private final SeparationRequirementUtil separationRequirementUtil;
  private final ConflictUtil conflictUtil;
  private final GeodeticCalc geodeticCalc = GeodeticCalc.Companion.geodeticCalcWSSS();


  public ConflictResponseUtil(WaypointsUtil wayPointUtil, TimeUtil timeUtil, SeparationRequirementUtil separationRequirementUtil, ConflictUtil conflictUtil) {
    this.waypointsUtil = wayPointUtil;
    this.timeUtil = timeUtil;
    this.separationRequirementUtil = separationRequirementUtil;
    this.conflictUtil = conflictUtil;
  }

  public List<Conflict> getConflicts(List<Trajectory> trajectoryList,
                                     List<SeparationRequirement> separationRequirements,
                                     int referenceTrajectoryIndex,
                                     GeoPoint referenceGeoPoint,
                                     Trajectory referenceTrajectory,
                                     long currentTime) {
    List<Conflict> conflictList = new ArrayList<>();

    for (int trajectoryIndex = 0; trajectoryIndex < trajectoryList.size(); trajectoryIndex++) { //loop all trajectories and compare
      if (trajectoryIndex == referenceTrajectoryIndex) continue; //skip for self
      Trajectory comparisonTrajectory = trajectoryList.get(trajectoryIndex);
      List<Waypoint> comparisonWaypointList =
          comparisonTrajectory.getWaypoints();
      int comparisonId = comparisonTrajectory.getId();

      if (timeUtil.timeNotWithinRangeOfWaypoints(currentTime,
          comparisonWaypointList)) continue;

      GeoPoint comparisonGeopoint =
          waypointsUtil.getGeoPointAtCurrentTime(comparisonWaypointList,
              currentTime, comparisonId);

      double separationRequirement =
          separationRequirementUtil.lateralSeparation(separationRequirements, referenceGeoPoint, comparisonGeopoint);

      if (geodeticCalc.distance(referenceGeoPoint, comparisonGeopoint) < separationRequirement) {
        Conflict conflict = conflictUtil.getConflict(currentTime, referenceTrajectory,
            comparisonTrajectory, separationRequirements);
        conflictList.add(conflict);
      }
    }
    return conflictList;
  }
}
