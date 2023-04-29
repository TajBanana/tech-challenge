package aero.airlab.challenge.conflictforecast.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureTest {

  @Test
  public void testType() {
    Feature feature = Feature.builder()
                             .type("Feature")
                             .geometry(Geometry.builder()
                                               .type("Point")
                                               .coordinates(List.of(new double[]{1.0, 2.0}))
                                               .build())
                             .properties(new Properties(12345L, "1 - 2"))
                             .build();
    assertEquals("Feature", feature.getType());
  }

  @Test
  public void testGeometry() {
    Geometry geometry = Geometry.builder()
                                .type("Point")
                                .coordinates(List.of(new double[]{1.0, 2.0}))
                                .build();
    Feature feature = Feature.builder()
                             .type("Feature")
                             .geometry(geometry)
                             .properties(new Properties(12345L, "1 - 2"))
                             .build();
    assertEquals(geometry, feature.getGeometry());
  }

  @Test
  public void testProperties() {
    Properties properties = new Properties(12345L, "1 - 2");
    Feature feature = Feature.builder()
                             .type("Feature")
                             .geometry(Geometry.builder()
                                               .type("Point")
                                               .coordinates(List.of(new double[]{1.0, 2.0}))
                                               .build())
                             .properties(properties)
                             .build();
    assertEquals(properties, feature.getProperties());
  }

  @Test
  public void testBuilder() {
    Geometry mockGeometry = Geometry.builder()
                                    .type("Point")
                                    .coordinates(List.of(new double[]{1.0, 2.0}))
                                    .build();

    Properties mockProperties = new Properties(12345L, "1 - 2");

    Feature feature = Feature.builder()
                             .type("Feature")
                             .geometry(mockGeometry)
                             .properties(mockProperties)
                             .build();
    Feature expectedFeature = new Feature("Feature", mockGeometry, mockProperties);
    assertEquals("Feature", feature.getType());
    assertEquals(expectedFeature.getGeometry(), feature.getGeometry());
    assertEquals(expectedFeature.getProperties(), feature.getProperties());
  }
}