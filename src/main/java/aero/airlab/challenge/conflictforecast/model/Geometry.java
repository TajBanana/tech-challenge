package aero.airlab.challenge.conflictforecast.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Geometry {
  private String type;
  private List<double[]> coordinates;
}
