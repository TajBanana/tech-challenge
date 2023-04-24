package aero.airlab.challenge.conflictforecast.util;

import aero.airlab.challenge.conflictforecast.api.SeparationRequirement;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.util.separationrequirement.SeparationRequirementUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SeparationRequirementUtilTest {

  @InjectMocks
  private SeparationRequirementUtil separationRequirementUtil;

  @Test
  void testLateralSeparationForSameZone() {
    GeoPoint geoPoint1 = new GeoPoint(0.0, 0.01);
    GeoPoint geoPoint2 = new GeoPoint(0.0, 0.02);
    SeparationRequirement requirement1 = new SeparationRequirement(
        new GeoPoint(0.0, 0.0), 5000.0, 5000.0);
    SeparationRequirement requirement2 = new SeparationRequirement(
        new GeoPoint(0.0, 0.0), 10000.0, 10000.0);

    assertEquals(5000.0, separationRequirementUtil.lateralSeparation(
        Arrays.asList(requirement1, requirement2), geoPoint1, geoPoint2), 0.0);
    assertEquals(5000.0, separationRequirementUtil.lateralSeparation(
        Arrays.asList(requirement1, requirement2), geoPoint2, geoPoint1), 0.0);
  }

  @Test
  void testLateralSeparationForSameZoneFar() {
    GeoPoint geoPoint1 = new GeoPoint(0.0, 0.07);
    GeoPoint geoPoint2 = new GeoPoint(0.0, 0.08);
    SeparationRequirement requirement1 = new SeparationRequirement(
        new GeoPoint(0.0, 0.0), 5000.0, 5000.0);
    SeparationRequirement requirement2 = new SeparationRequirement(
        new GeoPoint(0.0, 0.0), 10000.0, 10000.0);

    assertEquals(10000.0, separationRequirementUtil.lateralSeparation(
        Arrays.asList(requirement1, requirement2), geoPoint1, geoPoint2), 0.0);
    assertEquals(10000.0, separationRequirementUtil.lateralSeparation(
        Arrays.asList(requirement1, requirement2), geoPoint2, geoPoint1), 0.0);
  }

  @Test
  void testLateralSeparationForDifferentZone() {
    GeoPoint geoPoint1 = new GeoPoint(0.0, 0.01);
    GeoPoint geoPoint2 = new GeoPoint(0.0, 0.08);
    SeparationRequirement requirement1 = new SeparationRequirement(
        new GeoPoint(0.0, 0.0), 5000.0, 5000.0);
    SeparationRequirement requirement2 = new SeparationRequirement(
        new GeoPoint(0.0, 0.0), 10000.0, 10000.0);

    assertEquals(10000.0, separationRequirementUtil.lateralSeparation(
        Arrays.asList(requirement1, requirement2), geoPoint1, geoPoint2), 0.0);
    assertEquals(10000.0, separationRequirementUtil.lateralSeparation(
        Arrays.asList(requirement1, requirement2), geoPoint2, geoPoint1), 0.0);
  }

  @Test
  void testOutOfRangeTrue() {
    GeoPoint geoPoint1 = new GeoPoint(0.0, 0.01);
    GeoPoint geoPoint2 = new GeoPoint(0.0, 1.0);
    SeparationRequirement separationRequirement = new SeparationRequirement(
        new GeoPoint(0.0, 0.0), 10000.0, 10000.0);

    boolean outOfLargestRequirementRange = separationRequirementUtil.outOfLargestRequirementRange(separationRequirement,
        geoPoint1, geoPoint2);

    assertTrue(outOfLargestRequirementRange);

  }

  @Test
  void testOutOfRangeFalse() {
    GeoPoint geoPoint1 = new GeoPoint(0.0, 0.01);
    GeoPoint geoPoint2 = new GeoPoint(0.0, 0.05);
    SeparationRequirement separationRequirement = new SeparationRequirement(
        new GeoPoint(0.0, 0.0), 10000.0, 10000.0);

    boolean outOfLargestRequirementRange = separationRequirementUtil.outOfLargestRequirementRange(separationRequirement,
        geoPoint1, geoPoint2);

    assertFalse(outOfLargestRequirementRange);

  }
}