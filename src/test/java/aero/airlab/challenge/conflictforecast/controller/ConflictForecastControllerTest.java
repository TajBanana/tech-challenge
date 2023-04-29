package aero.airlab.challenge.conflictforecast.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.*;


import static aero.airlab.challenge.conflictforecast.testutil.MockTestRequest.mockRequestBody;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConflictForecastControllerTest {

  @Autowired
  public MockMvc mvc;

  @Autowired
  WebApplicationContext webApplicationContext;

/*  @Test
  void getConflictResponse() throws Exception {
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/v1/hello");
    MvcResult result = mvc.perform(requestBuilder).andReturn();
    System.out.println("result >>>> " + result.getResponse().getContentAsString());
  }*/

  @Test
  void getConflictResponse() throws Exception {
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/v1/conflict").content(mockRequestBody);
    MvcResult result = mvc.perform(requestBuilder).andReturn();
    String expectedResult = "{\"conflicts\":[{\"trajectoryA\":2,\"conflictStartA\":{\"lon\":103.90544056892395,\"lat\":1.8537336587905886,\"timestamp\":1569888058961},\"conflictEndA\":{\"lon\":103.71544792045663,\"lat\":2.7345832727817876,\"timestamp\":1569888538961},\"trajectoryB\":1418,\"conflictStartB\":{\"lon\":103.95119905471802,\"lat\":1.6840946674346926,\"timestamp\":1569888058961},\"conflictEndB\":{\"lon\":103.89267821634137,\"lat\":2.528611581968553,\"timestamp\":1569888538961}}]}";

    assertEquals(result.getResponse().getContentAsString(), expectedResult);
  }

  @Test
  void getConflictGeojsonResponse() {
  }
}