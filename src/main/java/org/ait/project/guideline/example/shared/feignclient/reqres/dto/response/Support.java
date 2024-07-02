package org.ait.project.guideline.example.shared.feignclient.reqres.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Support {

  @JsonProperty("text")
  private String text;

  @JsonProperty("url")
  private String url;
}