package aero.airlab.challenge.conflictforecast.testutil;

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
