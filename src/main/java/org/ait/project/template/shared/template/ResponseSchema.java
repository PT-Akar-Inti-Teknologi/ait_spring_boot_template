package org.ait.project.template.shared.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSchema {
  @JsonProperty("response_code")
  private String responseCode;
  @JsonProperty("response_message")
  private String responseMessage;
}
