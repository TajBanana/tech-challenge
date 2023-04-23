package aero.airlab.challenge.conflictforecast.service;

import aero.airlab.challenge.conflictforecast.mapper.ConflictRequestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static aero.airlab.challenge.conflictforecast.testutil.MockTestRequest.mockConflictForecastRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConflictRequestServiceTest {

  @Mock
  private ConflictRequestMapper conflictRequestMapper;

  @Mock
  private ConflictResponseService conflictResponseService;

  @InjectMocks
  private ConflictRequestService conflictRequestService;

  @Test
  void jsonToConflictRequest() {
    String jsonRequest = "{\"key\":\"value\"}";
    conflictRequestService.jsonToConflictRequest(jsonRequest);
    verify(conflictRequestMapper).jsonToRequest(jsonRequest);
  }

  @Test
  void checkConflict() {
    String jsonRequest = "{\"key\":\"value\"}";
    when(conflictRequestMapper.jsonToRequest(jsonRequest)).thenReturn(mockConflictForecastRequest);

    conflictRequestService.checkConflict(jsonRequest);
    verify(conflictRequestMapper).jsonToRequest(jsonRequest);
    verify(conflictResponseService).check(mockConflictForecastRequest);

  }
}