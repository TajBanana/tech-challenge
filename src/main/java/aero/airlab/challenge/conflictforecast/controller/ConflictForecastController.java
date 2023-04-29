package aero.airlab.challenge.conflictforecast.controller;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastResponse;
import aero.airlab.challenge.conflictforecast.mapper.FeatureCollectionMapper;
import aero.airlab.challenge.conflictforecast.model.FeatureCollection;
import aero.airlab.challenge.conflictforecast.model.Properties;
import aero.airlab.challenge.conflictforecast.service.ConflictRequestService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ConflictForecastController {

  private final ConflictRequestService conflictRequestService;
  private final FeatureCollectionMapper featureCollectionMapper;

  public ConflictForecastController(ConflictRequestService conflictRequestService, FeatureCollectionMapper featureCollectionMapper) {
    this.conflictRequestService = conflictRequestService;
    this.featureCollectionMapper = featureCollectionMapper;
  }

  @GetMapping(path = "/hello", produces =
      MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Properties> hello() {
    Properties properties =
        Properties.builder()
                  .conflictStartTime(12345)
                  .trajectories("string string")
                  .build();
    System.out.println(properties.toString());
    return ResponseEntity.ok(properties);
  }


  @GetMapping(path = "/conflict", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ConflictForecastResponse> getConflictResponse(@RequestBody String jsonConflictRequest) {
    ConflictForecastResponse response = conflictRequestService.checkConflict(jsonConflictRequest);
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/conflict/geojson", produces =
      MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FeatureCollection> getConflictGeoJsonResponse(@RequestBody String jsonConflictRequest) {
    ConflictForecastResponse response = conflictRequestService.checkConflict(jsonConflictRequest);
    return ResponseEntity.ok(featureCollectionMapper.fromConflicts(response.getConflicts()));
  }
}
