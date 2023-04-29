package aero.airlab.challenge.conflictforecast.mapper;

import aero.airlab.challenge.conflictforecast.api.Conflict;
import aero.airlab.challenge.conflictforecast.model.Feature;
import aero.airlab.challenge.conflictforecast.model.FeatureCollection;
import aero.airlab.challenge.conflictforecast.model.Geometry;
import aero.airlab.challenge.conflictforecast.model.Properties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeatureCollectionMapper {
  public FeatureCollection fromConflicts(List<Conflict> conflicts) {
    FeatureCollection featureCollection = FeatureCollection.builder()
                                                           .type("FeatureCollection")
                                                           .features(new ArrayList<>())
                                                           .build();

    for (Conflict conflict : conflicts) {
      double[][] coordinatesA = {
          {conflict.getConflictStartA().getLon(), conflict.getConflictStartA().getLat()},
          {conflict.getConflictEndA().getLon(), conflict.getConflictEndA().getLat()}
      };
      double[][] coordinatesB = {
          {conflict.getConflictStartB().getLon(), conflict.getConflictStartB().getLat()},
          {conflict.getConflictEndB().getLon(), conflict.getConflictEndB().getLat()}
      };

      Geometry geometryA = Geometry.builder()
                                   .type("LineString")
                                   .coordinates(List.of(coordinatesA))
                                   .build();

      Geometry geometryB = Geometry.builder()
                                   .type("LineString")
                                   .coordinates(List.of(coordinatesB))
                                   .build();

      Properties properties = Properties.builder()
                                        .conflictStartTime(conflict.getConflictStartA()
                                                                   .getTimestamp())
                                        .trajectories(String.format("%s - %s"
                                            , conflict.getTrajectoryA(),
                                            conflict.getTrajectoryB()))
                                        .build();

      Feature featureA = Feature.builder()
                               .type("Feature")
                               .geometry(geometryA)
                               .properties(properties)
                               .build();

      Feature featureB = Feature.builder()
                                .type("Feature")
                                .geometry(geometryB)
                                .properties(properties)
                                .build();
      featureCollection.getFeatures().add(featureA);
      featureCollection.getFeatures().add(featureB);
    }
    return featureCollection;
  }
}
