package aero.airlab.challenge.conflictforecast.util.conflict;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ConflictsMapUtil {
  private final Map<Integer, Set<Integer>> conflictsMap = new HashMap<>();

  public void put(int trajectoryA, int trajectoryB) {
    Set<Integer> updatedSetA = conflictsMap.getOrDefault(trajectoryA, new HashSet<>());
    updatedSetA.add(trajectoryB);
    conflictsMap.put(trajectoryA, updatedSetA);

    Set<Integer> updatedSetB = conflictsMap.getOrDefault(trajectoryB, new HashSet<>());
    updatedSetB.add(trajectoryA);
    conflictsMap.put(trajectoryB, updatedSetB);
  }

  public boolean conflictsSetContains(int referenceId, int comparisonId) {
    return conflictsMap.getOrDefault(referenceId, new HashSet<>())
                       .contains(comparisonId) ||
        conflictsMap.getOrDefault(comparisonId, new HashSet<>())
                    .contains(referenceId);
  }

  public void clear() {
    conflictsMap.clear();
  }
}
