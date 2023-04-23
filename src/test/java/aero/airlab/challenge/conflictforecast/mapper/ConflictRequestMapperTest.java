package aero.airlab.challenge.conflictforecast.mapper;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastRequest;
import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.api.Trajectory;
import aero.airlab.challenge.conflictforecast.api.Waypoint;
import aero.airlab.challenge.conflictforecast.exception.MapperException;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.testutil.MockTestRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ConflictRequestMapperTest {

  @Autowired
  private ConflictRequestMapper conflictRequestMapper;

  private final String validJsonRequest = MockTestRequest.getJsonRequest();

  @Test
  public void testValidJsonToRequest() {
    List<Waypoint> expectedWaypoints =
        new ArrayList<>(List.of(new Waypoint(101.5080714225769,
            10.933113098144531, 1569888059961L), new Waypoint(101.98527526855469,
            9.293333053588867, 1569888792000L)));

    Trajectory expectedTrajectory = new Trajectory(519, expectedWaypoints);
    List<Trajectory> expectedTrajectories = new ArrayList<>(List.of(expectedTrajectory));
    SeparationRequirement separationRequirement1 =
        new SeparationRequirement(new GeoPoint(104.0, 1.35), 55000.0, 20000.0);
    SeparationRequirement separationRequirement2 =
        new SeparationRequirement(new GeoPoint(104.0, 1.35), 1000000.0, 30000.0);

    List<SeparationRequirement> separationRequirements = new ArrayList<>();
    separationRequirements.add(separationRequirement1);
    separationRequirements.add(separationRequirement2);

    ConflictForecastRequest conflictForecastRequest = conflictRequestMapper.jsonToRequest(validJsonRequest);
    assertEquals(expectedTrajectories, conflictForecastRequest.getTrajectories());
    assertEquals(separationRequirements, conflictForecastRequest.getSeparationRequirements());
  }

  @Test
  public void testInvalidJsonRequest() {
    String mockInvalidRequest = """
              {
                "trajectories": [
        """;

    assertThrows(MapperException.class,
        () -> conflictRequestMapper.jsonToRequest(mockInvalidRequest));
  }

}