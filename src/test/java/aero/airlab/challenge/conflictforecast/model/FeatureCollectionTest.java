package aero.airlab.challenge.conflictforecast.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeatureCollectionTest {

  @Test
  public void testBuilder() {
    Geometry mockGeometry = Geometry.builder()
                                    .type("Point")
                                    .coordinates(List.of(new double[]{1.0, 2.0}))
                                    .build();

    Properties mockProperties = new Properties(12345L, "1 - 2");

    List<Feature> features = List.of(Feature.builder()
                                            .type("Feature")
                                            .geometry(mockGeometry)
                                            .properties(mockProperties)
                                            .build());
    FeatureCollection featureCollection =
        FeatureCollection.builder()
                         .type("FeatureCollection")
                         .features(features)
                         .build();

    assertEquals("FeatureCollection", featureCollection.getType());
    assertEquals(features, featureCollection.getFeatures());
  }

}