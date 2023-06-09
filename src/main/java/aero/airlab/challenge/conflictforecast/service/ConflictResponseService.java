package aero.airlab.challenge.conflictforecast.service;

import aero.airlab.challenge.conflictforecast.api.*;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.util.conflict.ConflictResponseUtil;
import aero.airlab.challenge.conflictforecast.util.conflict.ConflictsMapUtil;
import aero.airlab.challenge.conflictforecast.util.time.TimeUtil;
import aero.airlab.challenge.conflictforecast.util.waypoint.WaypointsUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictResponseService {

  private final WaypointsUtil waypointsUtil;
  private final TimeUtil timeUtil;
  private final ConflictResponseUtil conflictResponseUtil;
  private final ConflictsMapUtil conflictsMap;

  public ConflictResponseService(WaypointsUtil wayPointsUtil, TimeUtil timeUtil, ConflictResponseUtil conflictResponseUtil, ConflictsMapUtil conflictsMapUtil) {
    this.waypointsUtil = wayPointsUtil;
    this.timeUtil = timeUtil;
    this.conflictResponseUtil = conflictResponseUtil;
    this.conflictsMap = conflictsMapUtil;
  }

  public ConflictForecastResponse check(ConflictForecastRequest request) {
    List<Trajectory> trajectoryList = request.getTrajectories();
    List<SeparationRequirement> separationRequirements = request.getSeparationRequirements();
    List<Conflict> conflictsResult = new ArrayList<>();

    conflictsMap.clear();

    long minTime = timeUtil.getMinTime(trajectoryList);
    long maxTime = timeUtil.getMaxTime(trajectoryList);

    long currentTime = minTime;

    while (currentTime <= maxTime) {
      for (int referenceTrajectoryIndex = 0; referenceTrajectoryIndex < trajectoryList.size(); referenceTrajectoryIndex++) { //loop all trajectories
        Trajectory referenceTrajectory = trajectoryList.get(referenceTrajectoryIndex);
        List<Waypoint> referenceWaypoints = referenceTrajectory.getWaypoints();
        int referenceId = referenceTrajectory.getId();

        //skip if time is not within range
        if (timeUtil.notWithinRangeOfWaypoints(currentTime,
            referenceWaypoints)) continue;

        GeoPoint referenceGeoPoint =
            waypointsUtil.getGeoPointAtCurrentTime(referenceWaypoints,
                currentTime, referenceId);

        List<Conflict> conflicts = conflictResponseUtil.getConflicts(trajectoryList,
            separationRequirements,
            referenceGeoPoint,
            referenceTrajectory,
            currentTime);

        for (Conflict conflict: conflicts) {
          conflictsMap.put(conflict.getTrajectoryA(), conflict.getTrajectoryB());
        }

        conflictsResult.addAll(conflicts);
      }
      currentTime += 5000;
    }
    return new ConflictForecastResponse(conflictsResult);
  }
}
