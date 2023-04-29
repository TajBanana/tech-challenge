package aero.airlab.challenge.conflictforecast.util.separationrequirement;

import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.GeodeticCalc;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeparationRequirementUtil {
  public double lateralSeparation(List<SeparationRequirement> separationRequirementList, GeoPoint geoPoint1, GeoPoint geoPoint2) {
    double lateralSeparation =
        separationRequirementList.get(separationRequirementList.size() - 1)
                                 .getLateralSeparation();
    double geopoint1Separation = lateralSeparation;
    double geopoint2Separation = lateralSeparation;

    geopoint1Separation = getGeopointSeparation(separationRequirementList, geoPoint1, geopoint1Separation);
    geopoint2Separation = getGeopointSeparation(separationRequirementList,geoPoint2, geopoint2Separation);

    lateralSeparation = Math.max(geopoint1Separation, geopoint2Separation);

    return lateralSeparation;
  }

  private double getGeopointSeparation(List<SeparationRequirement> separationRequirementList, GeoPoint geoPoint, double geopointSeparation) {
    GeodeticCalc geodeticCalc = GeodeticCalc.Companion.geodeticCalcWSSS();

    for (int i = separationRequirementList.size() - 1; i >= 0; i--) {
      SeparationRequirement separationRequirement = separationRequirementList.get(i);

      GeoPoint centre =
          new GeoPoint(separationRequirement.getCenter().getLon(),
              separationRequirement.getCenter().getLat());

      double distance = geodeticCalc.distance(centre, geoPoint);

      if (distance < separationRequirement.getRadius()) {
        geopointSeparation = separationRequirement.getLateralSeparation();
      }
    }
    return geopointSeparation;
  }

  public boolean outOfLargestRequirementRange(SeparationRequirement separationRequirement, GeoPoint geoPoint1, GeoPoint geoPoint2) {
    GeodeticCalc geodeticCalc = GeodeticCalc.Companion.geodeticCalcWSSS();
    GeoPoint center = separationRequirement.getCenter();
    double radius = separationRequirement.getRadius();

    return geodeticCalc.distance(center, geoPoint1) > radius || geodeticCalc.distance(center, geoPoint2) > radius;

  }
}
