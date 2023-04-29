package aero.airlab.challenge.conflictforecast.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Properties {
  private long conflictStartTime;
  private String trajectories;
}
