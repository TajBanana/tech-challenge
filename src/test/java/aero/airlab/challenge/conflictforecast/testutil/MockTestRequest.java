package aero.airlab.challenge.conflictforecast.testutil;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastRequest;
import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.mapper.ConflictRequestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class MockTestRequest {
  static List<Waypoint> mockRequestWaypoints = new ArrayList<>(List.of(
      new Waypoint(101.5080714225769, 10.933113098144531, 1569888059961L),
      new Waypoint(101.98527526855469, 9.293333053588867, 1569888792000L)
  ));
  static Trajectory mockTrajectory = new Trajectory(519, mockRequestWaypoints);
  static List<SeparationRequirement> mockSeparationRequirements =
      new ArrayList<>(List.of(
          new SeparationRequirement(new GeoPoint(104.0, 1.35), 55000.0,
              20000.0),
          new SeparationRequirement(new GeoPoint(104.0, 1.35), 1000000.0, 30000.0)
      ));

  public static ConflictForecastRequest mockConflictForecastRequest =
      new ConflictForecastRequest(new ArrayList<>(List.of(mockTrajectory)),
          mockSeparationRequirements);

  static String mockRequestString = """
      {
        "trajectories": [
      {
            "id": 519,
            "waypoints": [
      {
                "lon": 101.5080714225769,
                "lat": 10.933113098144531,
                "alt": 33000.0,
                "timestamp": 1569888059961
              },
              {
                "lon": 101.98527526855469,
                "lat": 9.293333053588867,
                "alt": 33000.0,
                "timestamp": 1569888792000
              }
      ]}
      ],
        "separationRequirements": [
          {
            "center": {
              "lon": 104.0,
              "lat": 1.35
            },
            "radius": 55000.0,
            "lateralSeparation": 20000.0
          },
          {
            "center": {
              "lon": 104.0,
              "lat": 1.35
            },
            "radius": 1000000.0,
            "lateralSeparation": 30000.0
          }
        ]
      }""";

  public static String getJsonRequest() {
    return mockRequestString;
  }

  public static ConflictForecastRequest getConflictForecastRequest() {
    ConflictRequestMapper conflictRequestMapper =
        new ConflictRequestMapper(new ObjectMapper());

    return conflictRequestMapper.jsonToRequest(mockRequestString);
  }

}
