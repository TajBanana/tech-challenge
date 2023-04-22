package aero.airlab.challenge.conflictforecast.testutil;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastRequest;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MockJsonRequest {

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

  public static String get() {
    return mockRequestString;
  }

}
