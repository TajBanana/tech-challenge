package aero.airlab.challenge.conflictforecast.service;

import aero.airlab.challenge.conflictforecast.api.*;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.util.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictResponseService {

  @Getter
  private long minTime;
  @Getter
  private long maxTime;

  private final WaypointsUtil waypointsUtil;
  private final TimeUtil timeUtil;
  private final ConflictResponseUtil conflictResponseUtil;

  public ConflictResponseService(WaypointsUtil wayPointsUtil, TimeUtil timeUtil, ConflictResponseUtil conflictResponseUtil) {
    this.waypointsUtil = wayPointsUtil;
    this.timeUtil = timeUtil;
    this.conflictResponseUtil = conflictResponseUtil;
  }

  public ConflictForecastResponse check(ConflictForecastRequest request) {
    List<Trajectory> trajectoryList = request.component1();
    List<SeparationRequirement> separationRequirements = request.component2();
    List<Conflict> conflictList = new ArrayList<>();

    minTime = timeUtil.getMinTime(trajectoryList);
    maxTime = timeUtil.getMaxTime(trajectoryList);

    long currentTime = minTime;

    while (currentTime <= maxTime) {
      for (int referenceTrajectoryIndex = 0; referenceTrajectoryIndex < trajectoryList.size(); referenceTrajectoryIndex++) { //loop all trajectories
        Trajectory referenceTrajectory = trajectoryList.get(referenceTrajectoryIndex);
        List<Waypoint> referenceWaypoints = referenceTrajectory.getWaypoints();
        int referenceId = referenceTrajectory.getId();

        //skip if time is not within range
        if (timeUtil.timeNotWithinRangeOfWaypoints(currentTime,
            referenceWaypoints)) continue;

        GeoPoint referenceGeoPoint =
            waypointsUtil.getGeoPointAtCurrentTime(referenceWaypoints,
                currentTime, referenceId);

        List<Conflict> conflicts = conflictResponseUtil.getConflicts(trajectoryList,
            separationRequirements,
            referenceTrajectoryIndex,
            referenceGeoPoint,
            referenceTrajectory,
            currentTime);

        conflictList.addAll(conflicts);
      }
      currentTime += 5000;
    }
    return new ConflictForecastResponse(conflictList);
  }
}
