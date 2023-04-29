package aero.airlab.challenge.conflictforecast.mapper;

import aero.airlab.challenge.conflictforecast.api.Conflict;
import aero.airlab.challenge.conflictforecast.geospatial.TemporalGeoPoint;
import aero.airlab.challenge.conflictforecast.model.Feature;
import aero.airlab.challenge.conflictforecast.model.FeatureCollection;
import aero.airlab.challenge.conflictforecast.model.Geometry;
import aero.airlab.challenge.conflictforecast.model.Properties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeatureCollectionMapperTest {

  @Autowired
  FeatureCollectionMapper featureCollectionMapper;

  @Test
  public void testMapper() {
    TemporalGeoPoint mockStartA = new TemporalGeoPoint(0.0, 0.0, 0L);
    TemporalGeoPoint mockEndA = new TemporalGeoPoint(1.0, 1.0, 10_000L);
    TemporalGeoPoint mockStartB = new TemporalGeoPoint(1.0, 1.0, 0L);
    TemporalGeoPoint mockEndB = new TemporalGeoPoint(2.0, 2.0, 10_000L);
    Conflict conflict = new Conflict(
        1,
        mockStartA,
        mockEndA,
        2,
        mockStartB,
        mockEndB
        );

    FeatureCollection featureCollection = featureCollectionMapper.fromConflicts(List.of(conflict));

    Feature feature = featureCollection.getFeatures().get(0);
    Geometry geometry = feature.getGeometry();
    Properties properties = feature.getProperties();
    String type = feature.getType();

    assertEquals("FeatureCollection", featureCollection.getType());
    assertEquals("LineString",geometry.getType());
    assertEquals(0.0,geometry.getCoordinates().get(0)[0]);
    assertEquals(0.0,geometry.getCoordinates().get(0)[1]);
    assertEquals(1.0,geometry.getCoordinates().get(1)[0]);
    assertEquals(1.0,geometry.getCoordinates().get(1)[1]);

    assertEquals("1 - 2",properties.getTrajectories());
    assertEquals(0L,properties.getConflictStartTime());

    assertEquals("Feature", type);

  }
}