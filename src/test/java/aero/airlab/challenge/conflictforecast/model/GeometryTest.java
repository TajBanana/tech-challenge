package aero.airlab.challenge.conflictforecast.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GeometryTest {

  @Test
  public void testType() {
    Geometry geometry = Geometry.builder()
                                .type("Point")
                                .coordinates(List.of(new double[]{1.0, 2.0}))
                                .build();
    assertEquals("Point", geometry.getType());
  }

  @Test
  public void testCoordinates() {
    List<double[]> coordinates = List.of(new double[]{1.0, 2.0});
    Geometry geometry = Geometry.builder()
                                .type("Point")
                                .coordinates(coordinates)
                                .build();
    assertEquals(coordinates, geometry.getCoordinates());
  }

  @Test
  public void testBuilder() {
    Geometry geometry2 = Geometry.builder()
                                 .type("LineString")
                                 .coordinates(Arrays.asList(new double[]{3.0, 4.0}, new double[]{5.0, 6.0}))
                                 .build();
    assertEquals("LineString", geometry2.getType());
    assertArrayEquals(new double[] {3.0, 4.0}, geometry2.getCoordinates().get(0));
    assertArrayEquals(new double[] {5.0, 6.0}, geometry2.getCoordinates().get(1));
  }

}
