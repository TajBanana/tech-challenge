package aero.airlab.challenge.conflictforecast.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class RequestFileReader {
  public String getRequest(int fileNumber) {

    String fileName = String.format("demo-request-%s.json", fileNumber);
    return getJsonString(fileName);
  }

  @NotNull
  private String getJsonString(String fileName) {
    try {
      ClassPathResource resource = new ClassPathResource(fileName);
      InputStream inputStream = resource.getInputStream();
      byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
      return new String(bytes, StandardCharsets.UTF_8);

    } catch (IOException e) {
      e.printStackTrace();
      return "error";
    }
  }
}
