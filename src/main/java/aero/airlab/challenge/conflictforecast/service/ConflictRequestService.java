package aero.airlab.challenge.conflictforecast.service;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastRequest;
import aero.airlab.challenge.conflictforecast.api.ConflictForecastResponse;
import aero.airlab.challenge.conflictforecast.mapper.ConflictRequestMapper;
import org.springframework.stereotype.Service;

@Service
public class ConflictRequestService {
  private final ConflictRequestMapper conflictRequestMapper;
  private final ConflictResponseService conflictResponseService;

  public ConflictRequestService(ConflictRequestMapper conflictRequestMapper, ConflictResponseService conflictResponseService) {
    this.conflictRequestMapper = conflictRequestMapper;
    this.conflictResponseService = conflictResponseService;
  }

  public ConflictForecastRequest jsonToConflictRequest(String jsonRequest) {
    return conflictRequestMapper.jsonToRequest(jsonRequest);
  }

  public ConflictForecastResponse checkConflict(String jsonRequest) {
    ConflictForecastRequest request = jsonToConflictRequest(jsonRequest);
    //System.out.println("From ConflictRequest Service");
    //System.out.println(request);

    return conflictResponseService.check(request);
  }
}
