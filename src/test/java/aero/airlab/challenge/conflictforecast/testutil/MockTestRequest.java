package aero.airlab.challenge.conflictforecast.testutil;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastRequest;
import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;

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

  public static String mockGeoJsonRequestBody = """
      {"trajectories": [
      {
      "id":2,
      "waypoints":[
      {
      "lon":103.90544056892395,
      "lat":1.8537336587905884,
      "timestamp":1569888058961
      },
      {
      "lon":103.88783264160156,
      "lat":2.132054328918457,
      "timestamp":1569888215000
      },
      {
      "lon":103.87166595458984,
      "lat":2.388333320617676,
      "timestamp":1569888351000
      },
      {
      "lon":103.71888732910156,
      "lat":2.7269444465637207,
      "timestamp":1569888535000
      },
      {
      "lon":103.6433334350586,
      "lat":2.8947222232818604,
      "timestamp":1569888622000
      },
      {
      "lon":103.64360809326172,
      "lat":2.8949999809265137,
      "timestamp":1569888633000
      },
      {
      "lon":103.42256927490234,
      "lat":3.3825764656066895,
      "timestamp":1569888885000
      },
      {
      "lon":102.90067291259766,
      "lat":4.69732141494751,
      "timestamp":1569889492000
      },
      {
      "lon":102.56277465820312,
      "lat":5.542500019073486,
      "timestamp":1569889899000
      },
      {
      "lon":102.31416320800781,
      "lat":6.163610935211182,
      "timestamp":1569890198000
      }
      ]
      },
      {
      "id":1418,
      "waypoints":[
      {
      "lon":103.95119905471802,
      "lat":1.6840946674346924,
      "timestamp":1569888058961
      },
      {
      "lon":103.91194152832031,
      "lat":1.7561111450195312,
      "timestamp":1569888113000
      },
      {
      "lon":103.8877182006836,
      "lat":2.133012056350708,
      "timestamp":1569888333000
      },
      {
      "lon":103.87166595458984,
      "lat":2.388333320617676,
      "timestamp":1569888470000
      },
      {
      "lon":103.94999694824219,
      "lat":2.9111111164093018,
      "timestamp":1569888727000
      },
      {
      "lon":103.974365234375,
      "lat":3.071185827255249,
      "timestamp":1569888804000
      },
      {
      "lon":104.01305389404297,
      "lat":3.326111078262329,
      "timestamp":1569888926000
      },
      {
      "lon":104.0660171508789,
      "lat":3.6779234409332275,
      "timestamp":1569889095000
      },
      {
      "lon":104.09500122070312,
      "lat":3.8730554580688477,
      "timestamp":1569889188000
      },
      {
      "lon":104.24500274658203,
      "lat":4.873054027557373,
      "timestamp":1569889664000
      },
      {
      "lon":104.13249969482422,
      "lat":6.989616394042969,
      "timestamp":1569890674000
      },
      {
      "lon":104.13194274902344,
      "lat":7.0,
      "timestamp":1569890678000
      },
      {
      "lon":104.11805725097656,
      "lat":7.2572221755981445,
      "timestamp":1569890801000
      }
      ]
      }
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
      ]}""";

  public static String mockRequestBody = """
      {"trajectories": [
      {
      "id":2,
      "waypoints":[
      {
      "lon":103.90544056892395,
      "lat":1.8537336587905884,
      "timestamp":1569888058961
      },
      {
      "lon":103.88783264160156,
      "lat":2.132054328918457,
      "timestamp":1569888215000
      },
      {
      "lon":103.87166595458984,
      "lat":2.388333320617676,
      "timestamp":1569888351000
      },
      {
      "lon":103.71888732910156,
      "lat":2.7269444465637207,
      "timestamp":1569888535000
      },
      {
      "lon":103.6433334350586,
      "lat":2.8947222232818604,
      "timestamp":1569888622000
      },
      {
      "lon":103.64360809326172,
      "lat":2.8949999809265137,
      "timestamp":1569888633000
      },
      {
      "lon":103.42256927490234,
      "lat":3.3825764656066895,
      "timestamp":1569888885000
      },
      {
      "lon":102.90067291259766,
      "lat":4.69732141494751,
      "timestamp":1569889492000
      },
      {
      "lon":102.56277465820312,
      "lat":5.542500019073486,
      "timestamp":1569889899000
      },
      {
      "lon":102.31416320800781,
      "lat":6.163610935211182,
      "timestamp":1569890198000
      }
      ]
      },
      {
      "id":1418,
      "waypoints":[
      {
      "lon":103.95119905471802,
      "lat":1.6840946674346924,
      "timestamp":1569888058961
      },
      {
      "lon":103.91194152832031,
      "lat":1.7561111450195312,
      "timestamp":1569888113000
      },
      {
      "lon":103.8877182006836,
      "lat":2.133012056350708,
      "timestamp":1569888333000
      },
      {
      "lon":103.87166595458984,
      "lat":2.388333320617676,
      "timestamp":1569888470000
      },
      {
      "lon":103.94999694824219,
      "lat":2.9111111164093018,
      "timestamp":1569888727000
      },
      {
      "lon":103.974365234375,
      "lat":3.071185827255249,
      "timestamp":1569888804000
      },
      {
      "lon":104.01305389404297,
      "lat":3.326111078262329,
      "timestamp":1569888926000
      },
      {
      "lon":104.0660171508789,
      "lat":3.6779234409332275,
      "timestamp":1569889095000
      },
      {
      "lon":104.09500122070312,
      "lat":3.8730554580688477,
      "timestamp":1569889188000
      },
      {
      "lon":104.24500274658203,
      "lat":4.873054027557373,
      "timestamp":1569889664000
      },
      {
      "lon":104.13249969482422,
      "lat":6.989616394042969,
      "timestamp":1569890674000
      },
      {
      "lon":104.13194274902344,
      "lat":7.0,
      "timestamp":1569890678000
      },
      {
      "lon":104.11805725097656,
      "lat":7.2572221755981445,
      "timestamp":1569890801000
      }
      ]
      }
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
        ]}""";
}
