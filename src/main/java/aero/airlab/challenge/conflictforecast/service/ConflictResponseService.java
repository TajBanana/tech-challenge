package aero.airlab.challenge.conflictforecast.service;

import aero.airlab.challenge.conflictforecast.api.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class ConflictResponseService {
  private long minTime;
  private long maxTime;

  public ConflictForecastResponse check(ConflictForecastRequest request) {
    List<Trajectory> trajectoryList = request.component1();
    List<SeparationRequirement> separationRequirements = request.component2();

    getMinMaxTime(trajectoryList);
    //TODO return conflict response
    return null;
  }

  private void getMinMaxTime(List<Trajectory> trajectoryList) {
    this.minTime = Long.MAX_VALUE;
    this.maxTime = Long.MIN_VALUE;

    for (Trajectory trajectory: trajectoryList) {
      List<Waypoint> waypoints = trajectory.component2();

      for (Waypoint waypoint: waypoints) {
        long time = waypoint.component3();
        this.minTime = Math.min(minTime, time);
        this.maxTime = Math.max(maxTime, time);
      }
    }
    System.out.println("MIN: " +  minTime);
    System.out.println("MAX: " +  maxTime);

  }


}
