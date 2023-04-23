package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeUtil {

  public long getMinTime(List<Trajectory> trajectoryList) {
    long minTime = Long.MAX_VALUE;

    for (Trajectory trajectory : trajectoryList) {
      List<Waypoint> waypoints = trajectory.component2();

      for (Waypoint waypoint : waypoints) {
        long time = waypoint.component3();
        minTime = Math.min(minTime, time);
      }
    }
    return minTime;
  }

  public long getMaxTime(List<Trajectory> trajectoryList) {
    long maxTime = Long.MIN_VALUE;

    for (Trajectory trajectory : trajectoryList) {
      List<Waypoint> waypoints = trajectory.component2();

      for (Waypoint waypoint : waypoints) {
        long time = waypoint.component3();
        maxTime = Math.max(maxTime, time);
      }
    }
    return maxTime;
  }

  public long getWayPointsMaxTime(Trajectory trajectory) {
    long maxTime = Long.MIN_VALUE;

    List<Waypoint> waypoints = trajectory.getWaypoints();
    for (Waypoint waypoint : waypoints) {
      long time = waypoint.component3();
      maxTime = Math.max(maxTime, time);
    }
    return maxTime;
  }

  public boolean timeNotWithinRangeOfWaypoints(long currentTime,
                                               List<Waypoint> waypoints) {
    return currentTime < waypoints.get(0).getTimestamp() ||
        currentTime > waypoints.get(waypoints.size() - 1)
                               .getTimestamp();
  }
}
