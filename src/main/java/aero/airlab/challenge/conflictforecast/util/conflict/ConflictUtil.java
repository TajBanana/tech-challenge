package aero.airlab.challenge.conflictforecast.util.conflict;

import aero.airlab.challenge.conflictforecast.api.Conflict;
import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeodeticCalc;
import aero.airlab.challenge.conflictforecast.geospatial.TemporalGeoPoint;
import aero.airlab.challenge.conflictforecast.util.separationrequirement.SeparationRequirementUtil;
import aero.airlab.challenge.conflictforecast.util.time.TimeUtil;
import aero.airlab.challenge.conflictforecast.util.waypoint.WaypointsUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConflictUtil {

  private final WaypointsUtil waypointUtil;
  private final SeparationRequirementUtil separationRequirementUtil;
  private final TimeUtil timeUtil;
  private final GeodeticCalc geodeticCalc = GeodeticCalc.Companion.geodeticCalcWSSS();

  public ConflictUtil(WaypointsUtil waypointUtil, SeparationRequirementUtil separationRequirementUtil, TimeUtil timeUtil) {
    this.waypointUtil = waypointUtil;
    this.separationRequirementUtil = separationRequirementUtil;
    this.timeUtil = timeUtil;
  }

  public Conflict getConflict(long time,
                              Trajectory referenceTrajectory,
                              Trajectory comparisonTrajectory,
                              List<SeparationRequirement> separationRequirements) {

    int trajectoryA = referenceTrajectory.getId();
    int trajectoryB = comparisonTrajectory.getId();
    List<Waypoint> referenceWaypointList = referenceTrajectory.getWaypoints();
    List<Waypoint> comparisonWaypointList = comparisonTrajectory.getWaypoints();

    GeoPoint referenceGeoPoint =
        waypointUtil.getGeoPointAtCurrentTime(referenceWaypointList,
        time, referenceTrajectory.getId());
    GeoPoint comparisonGeoPoint =
        waypointUtil.getGeoPointAtCurrentTime(comparisonWaypointList,
            time, comparisonTrajectory.getId());

    TemporalGeoPoint conflictStartA =
        new TemporalGeoPoint(referenceGeoPoint.getLon(),
            referenceGeoPoint.getLat(), time);
    TemporalGeoPoint conflictStartB =
        new TemporalGeoPoint(comparisonGeoPoint.getLon(),
            comparisonGeoPoint.getLat(), time);

    TemporalGeoPoint conflictEndA = null;
    TemporalGeoPoint conflictEndB = null;

    GeoPoint geoPointA = referenceGeoPoint;
    GeoPoint geoPointB = comparisonGeoPoint;

    double separationRequirement =
        separationRequirementUtil.lateralSeparation(separationRequirements, geoPointA, geoPointB);

    long currentTime = time;
    long maxTime = Math.min(timeUtil.getWayPointsMaxTime(referenceTrajectory),
        timeUtil.getWayPointsMaxTime(comparisonTrajectory));

    while (geodeticCalc.distance(geoPointA, geoPointB) < separationRequirement && currentTime <= maxTime) {

      geoPointA =
          waypointUtil.getGeoPointAtCurrentTime(referenceWaypointList,
              currentTime, referenceTrajectory.getId());
      geoPointB =
          waypointUtil.getGeoPointAtCurrentTime(comparisonWaypointList,
              currentTime, comparisonTrajectory.getId());

      separationRequirement =
          separationRequirementUtil.lateralSeparation(separationRequirements, geoPointA, geoPointB);

      conflictEndA =
          new TemporalGeoPoint(geoPointA.getLon(),
              geoPointA.getLat(), currentTime);

      conflictEndB =
          new TemporalGeoPoint(geoPointB.getLon(),
              geoPointB.getLat(), currentTime);

      currentTime += 5000;
    }

    return new Conflict(trajectoryA, conflictStartA, conflictEndA,
        trajectoryB, conflictStartB, conflictEndB);
  }
}
