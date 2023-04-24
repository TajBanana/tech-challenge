package aero.airlab.challenge.conflictforecast.util.conflict;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ConflictsMapUtilTest {
  @Test
  void testPut() {
    ConflictsMapUtil conflictsMapUtil = new ConflictsMapUtil();
    conflictsMapUtil.put(1, 2);
    conflictsMapUtil.put(2, 3);
    conflictsMapUtil.put(1, 3);

    Map<Integer, Set<Integer>> expectedConflictsMap = new HashMap<>();
    expectedConflictsMap.put(1, Set.of(2, 3));
    expectedConflictsMap.put(2, Set.of(1, 3));
    expectedConflictsMap.put(3, Set.of(1, 2));

    assertEquals(expectedConflictsMap, conflictsMapUtil.getConflictsMap());
  }

  @Test
  void testConflictsSetContains() {
    ConflictsMapUtil conflictsMapUtil = new ConflictsMapUtil();
    conflictsMapUtil.put(1, 2);
    conflictsMapUtil.put(2, 3);
    conflictsMapUtil.put(1, 3);

    assertTrue(conflictsMapUtil.conflictsSetContains(1, 2));
    assertTrue(conflictsMapUtil.conflictsSetContains(2, 3));
    assertTrue(conflictsMapUtil.conflictsSetContains(1, 3));
    assertFalse(conflictsMapUtil.conflictsSetContains(1, 4));
  }

  @Test
  void testClear() {
    ConflictsMapUtil conflictsMapUtil = new ConflictsMapUtil();
    conflictsMapUtil.put(1, 2);
    conflictsMapUtil.put(2, 3);
    conflictsMapUtil.put(1, 3);

    conflictsMapUtil.clear();
    assertTrue(conflictsMapUtil.getConflictsMap().isEmpty());
  }

}