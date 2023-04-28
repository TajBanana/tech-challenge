package aero.airlab.challenge.conflictforecast.service;

import aero.airlab.challenge.conflictforecast.api.*;
import aero.airlab.challenge.conflictforecast.geospatial.GeoPoint;
import aero.airlab.challenge.conflictforecast.geospatial.TemporalGeoPoint;
import aero.airlab.challenge.conflictforecast.util.conflict.ConflictResponseUtil;
import aero.airlab.challenge.conflictforecast.util.conflict.ConflictsMapUtil;
import aero.airlab.challenge.conflictforecast.util.time.TimeUtil;
import aero.airlab.challenge.conflictforecast.util.waypoint.WaypointsUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConflictResponseServiceTest {
  @Mock
  private WaypointsUtil waypointsUtil;
  @Mock
  private TimeUtil timeUtil;
  @Mock
  private ConflictResponseUtil conflictResponseUtil;
  @Mock
  private ConflictsMapUtil conflictsMap;

  @InjectMocks
  private ConflictResponseService conflictResponseService;

  @Test
  public void testCheckNoConflicts() {
    Trajectory trajectory1 =
        new Trajectory(1, Arrays.asList(new Waypoint(0, 0, 0), new Waypoint(2
            , 0, 20000)));
    Trajectory trajectory2 =
        new Trajectory(2, Arrays.asList(new Waypoint(3, 0, 0), new Waypoint(5
            , 0, 20000)));
    SeparationRequirement separationRequirement =
        new SeparationRequirement(new GeoPoint(0.0, 0.0)
            , 2000, 10000);
    ConflictForecastRequest request = new ConflictForecastRequest(Arrays.asList(trajectory1, trajectory2), List.of(separationRequirement));

    when(timeUtil.getMinTime(anyList())).thenReturn(0L);
    when(timeUtil.getMaxTime(anyList())).thenReturn(20000L);
    when(timeUtil.notWithinRangeOfWaypoints(anyLong(), anyList())).thenReturn(false);
    when(waypointsUtil.getGeoPointAtCurrentTime(anyList(), anyLong(), anyInt())).thenReturn(new GeoPoint(0, 0));
    when(conflictResponseUtil.getConflicts(anyList(), anyList(), any(GeoPoint.class), any(Trajectory.class), anyLong())).thenReturn(Collections.emptyList());

    ConflictForecastResponse response = conflictResponseService.check(request);

    assertTrue(response.getConflicts().isEmpty());
    verify(conflictsMap, times(1)).clear();
    verify(timeUtil, times(1)).getMinTime(anyList());
    verify(timeUtil, times(1)).getMaxTime(anyList());
  }

  @Test
  public void testCheckConflicts() {
    Trajectory trajectory1 =
        new Trajectory(1, Arrays.asList(new Waypoint(0, 0, 0), new Waypoint(2
            , 0, 10000)));
    Trajectory trajectory2 =
        new Trajectory(2, Arrays.asList(new Waypoint(0, 0, 0), new Waypoint(2
            , 0, 10000)));
    SeparationRequirement separationRequirement =
        new SeparationRequirement(new GeoPoint(0.0, 0.0)
            , 2000, 10000);
    ConflictForecastRequest request = new ConflictForecastRequest(Arrays.asList(trajectory1, trajectory2), List.of(separationRequirement));

    Conflict mockConflict = new Conflict(1, new TemporalGeoPoint(0.0, 0.0, 0L),
        new TemporalGeoPoint(2.0, 0.0, 10000L), 2, new TemporalGeoPoint(0.0,
        0.0, 0L), new TemporalGeoPoint(2.0, 0.0, 10000L));

    when(timeUtil.getMinTime(anyList())).thenReturn(0L);
    when(timeUtil.getMaxTime(anyList())).thenReturn(10000L);
    when(timeUtil.notWithinRangeOfWaypoints(anyLong(), anyList())).thenReturn(false);
    when(waypointsUtil.getGeoPointAtCurrentTime(anyList(), anyLong(), anyInt())).thenReturn(new GeoPoint(0, 0));
    when(conflictResponseUtil.getConflicts(anyList(), anyList(),
        any(GeoPoint.class), any(Trajectory.class), anyLong())).thenReturn(List.of(mockConflict));

    ConflictForecastResponse response = conflictResponseService.check(request);

    verify(conflictsMap, times(6)).put(anyInt(), anyInt());
    assertFalse(response.getConflicts().isEmpty());
  }

  @Test
  public void testCheckTimeNotWithinRange() {
    Trajectory trajectory1 =
        new Trajectory(1, Arrays.asList(new Waypoint(0, 0, 20000L),
            new Waypoint(2
                , 0, 20000L)));
    Trajectory trajectory2 =
        new Trajectory(2, Arrays.asList(new Waypoint(0, 0, 0), new Waypoint(2
            , 0, 20000L)));
    SeparationRequirement separationRequirement =
        new SeparationRequirement(new GeoPoint(0.0, 0.0)
            , 2000, 10000);
    ConflictForecastRequest request = new ConflictForecastRequest(Arrays.asList(trajectory1, trajectory2), List.of(separationRequirement));

    when(timeUtil.getMinTime(anyList())).thenReturn(0L);
    when(timeUtil.getMaxTime(anyList())).thenReturn(10000L);
    when(timeUtil.notWithinRangeOfWaypoints(anyLong(), anyList())).thenReturn(true);

    ConflictForecastResponse response = conflictResponseService.check(request);

    verify(conflictsMap, times(0)).put(anyInt(), anyInt());
    verify(waypointsUtil, times(0)).getGeoPointAtCurrentTime(anyList(),
        anyLong(),
        anyInt());
    assertTrue(response.getConflicts().isEmpty());
  }
}