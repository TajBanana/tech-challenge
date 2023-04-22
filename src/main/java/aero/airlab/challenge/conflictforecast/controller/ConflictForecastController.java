package aero.airlab.challenge.conflictforecast.controller;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastRequest;
import aero.airlab.challenge.conflictforecast.api.ConflictForecastResponse;
import aero.airlab.challenge.conflictforecast.service.ConflictRequestService;
import aero.airlab.challenge.conflictforecast.util.RequestFileReader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1")
public class ConflictForecastController {

  private final RequestFileReader fileReader;
  private final ConflictRequestService conflictRequestService;

  public ConflictForecastController(RequestFileReader fileReader, ConflictRequestService conflictRequestService) {
    this.fileReader = fileReader;
    this.conflictRequestService = conflictRequestService;
  }

  @GetMapping(path = "/hello/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ConflictForecastRequest> hello(@PathVariable int number) {

    String jsonConflictRequest = fileReader.getRequest(number);
    return ResponseEntity.ok(conflictRequestService.jsonToConflictRequest(jsonConflictRequest));
  }

  @GetMapping(path = "/conflict", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ConflictForecastResponse> getConflictResponse(@RequestBody String jsonConflictRequest) {
    ConflictForecastResponse response =
        conflictRequestService.checkConflict(jsonConflictRequest);
    return ResponseEntity.ok(response);
  }

}
