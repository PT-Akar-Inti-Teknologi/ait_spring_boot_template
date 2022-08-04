package org.ait.project.template.shared.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {

  @JsonProperty("field")
  private String field;

  @JsonProperty("message")
  private String message;
}
