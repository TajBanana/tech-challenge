package aero.airlab.challenge.conflictforecast.mapper;

import aero.airlab.challenge.conflictforecast.api.ConflictForecastRequest;
import aero.airlab.challenge.conflictforecast.exception.MapperException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConflictRequestMapper {
  private final ObjectMapper objectMapper;

  public ConflictRequestMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public ConflictForecastRequest jsonToRequest(String jsonRequest) {
    try {
      return objectMapper.readValue(jsonRequest, ConflictForecastRequest.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new MapperException(e.getMessage());
    }
  }
}
