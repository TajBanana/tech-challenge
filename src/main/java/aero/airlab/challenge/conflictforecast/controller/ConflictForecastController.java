package aero.airlab.challenge.conflictforecast.controller;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastResponse;
import aero.airlab.challenge.conflictforecast.service.ConflictRequestService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ConflictForecastController {

  private final ConflictRequestService conflictRequestService;

  public ConflictForecastController(ConflictRequestService conflictRequestService) {
    this.conflictRequestService = conflictRequestService;
  }

  @GetMapping(path = "/conflict", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ConflictForecastResponse> getConflictResponse(@RequestBody String jsonConflictRequest) {
    ConflictForecastResponse response = conflictRequestService.checkConflict(jsonConflictRequest);
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/conflict/geojson", produces =
      MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ConflictForecastResponse> getConflictGeoJsonResponse(@RequestBody String jsonConflictRequest) {
    ConflictForecastResponse response = conflictRequestService.checkConflict(jsonConflictRequest);
    return ResponseEntity.ok(response);
  }
}
