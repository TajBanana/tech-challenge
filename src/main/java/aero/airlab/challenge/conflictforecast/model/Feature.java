package aero.airlab.challenge.conflictforecast.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Feature {
  private String type;
  private Geometry geometry;
  private Properties properties;
}
