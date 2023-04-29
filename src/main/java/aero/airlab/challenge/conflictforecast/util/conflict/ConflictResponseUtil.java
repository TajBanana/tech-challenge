package aero.airlab.challenge.conflictforecast.util.conflict;

import aero.airlab.challenge.conflictforecast.api.Conflict;
import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeodeticCalc;
import aero.airlab.challenge.conflictforecast.util.separationrequirement.SeparationRequirementUtil;
import aero.airlab.challenge.conflictforecast.util.time.TimeUtil;
import aero.airlab.challenge.conflictforecast.util.waypoint.WaypointsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConflictResponseUtil {
  private final WaypointsUtil waypointsUtil;
  private final TimeUtil time;
  private final ConflictsMapUtil conflictsMap;
  private final SeparationRequirementUtil separationRequirementUtil;
  private final ConflictUtil conflictUtil;
  private final GeodeticCalc geodeticCalc = GeodeticCalc.Companion.geodeticCalcWSSS();
  private final Logger logger =
      LoggerFactory.getLogger(ConflictResponseUtil.class);

  public ConflictResponseUtil(WaypointsUtil wayPointUtil, TimeUtil timeUtil, ConflictsMapUtil conflictsMap, SeparationRequirementUtil separationRequirementUtil, ConflictUtil conflictUtil) {
    this.waypointsUtil = wayPointUtil;
    this.time = timeUtil;
    this.conflictsMap = conflictsMap;
    this.separationRequirementUtil = separationRequirementUtil;
    this.conflictUtil = conflictUtil;
  }

  public List<Conflict> getConflicts(List<Trajectory> trajectoryList,
                                     List<SeparationRequirement> separationRequirements,
                                     GeoPoint referenceGeoPoint,
                                     Trajectory referenceTrajectory,
                                     long currentTime) {
    List<Conflict> conflictList = new ArrayList<>();

    //loop all trajectories and compare
    for (Trajectory comparisonTrajectory : trajectoryList) {
      List<Waypoint> comparisonWaypointList =
          comparisonTrajectory.getWaypoints();
      int comparisonId = comparisonTrajectory.getId();
      int referenceId = referenceTrajectory.getId();

      //skip for self
      if (referenceId == comparisonId) continue;
      //skip if already checked
      if (conflictsMap.conflictsSetContains(referenceId, comparisonId))
        continue;
      //skip if time not within range
      if (time.notWithinRangeOfWaypoints(currentTime, comparisonWaypointList))
        continue;

      GeoPoint comparisonGeopoint =
          waypointsUtil.getGeoPointAtCurrentTime(comparisonWaypointList,
              currentTime, comparisonId);

      //skip if outside of requirements
      if (separationRequirementUtil.outOfLargestRequirementRange(separationRequirements.get(separationRequirements.size() - 1), referenceGeoPoint, comparisonGeopoint))
        continue;

      double separationRequirement =
          separationRequirementUtil.lateralSeparation(separationRequirements, referenceGeoPoint, comparisonGeopoint);
      double distance = geodeticCalc.distance(referenceGeoPoint, comparisonGeopoint);

      if (distance < separationRequirement) {
        Conflict conflict = conflictUtil.getConflict(currentTime, referenceTrajectory,
            comparisonTrajectory, separationRequirements);
        conflictList.add(conflict);
        logger.warn("Conflict detected at time: {} with " +
                "distance|separationRequirement: {}|{}. Conflicts: {}",
            currentTime, distance,
            separationRequirement, conflictList);
      }
    }
    return conflictList;
  }
}
