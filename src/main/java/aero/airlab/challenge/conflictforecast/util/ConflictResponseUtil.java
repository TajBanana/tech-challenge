package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Conflict;
import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeodeticCalc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final Logger logger =
      LoggerFactory.getLogger(ConflictResponseUtil.class);

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
      //skip for self
      if (trajectoryIndex == referenceTrajectoryIndex) continue;
      Trajectory comparisonTrajectory = trajectoryList.get(trajectoryIndex);
      List<Waypoint> comparisonWaypointList =
          comparisonTrajectory.getWaypoints();
      int comparisonId = comparisonTrajectory.getId();

      //skip if time not within range
      if (timeUtil.timeNotWithinRangeOfWaypoints(currentTime,
          comparisonWaypointList)) continue;

      GeoPoint comparisonGeopoint =
          waypointsUtil.getGeoPointAtCurrentTime(comparisonWaypointList,
              currentTime, comparisonId);

      //skip if outside of requirements
      if (separationRequirementUtil.outOfLargestRequirementRange(separationRequirements.get(separationRequirements.size() - 1), referenceGeoPoint, comparisonGeopoint))
        continue;

      double separationRequirement =
          separationRequirementUtil.lateralSeparation(separationRequirements, referenceGeoPoint, comparisonGeopoint);

      if (geodeticCalc.distance(referenceGeoPoint, comparisonGeopoint) < separationRequirement) {
        Conflict conflict = conflictUtil.getConflict(currentTime, referenceTrajectory,
            comparisonTrajectory, separationRequirements);
        conflictList.add(conflict);
        logger.warn("Conflict detected at currentTime: {} with " +
            "separationRequirement: {}. Conflicts: {}", currentTime,
            separationRequirement, conflictList );
      }
    }
    return conflictList;
  }
}
