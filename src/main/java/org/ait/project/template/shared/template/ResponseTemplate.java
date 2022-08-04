package org.ait.project.template.shared.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate<T> {
  @JsonProperty("response_schema")
  private ResponseSchema responseSchema;
  @JsonProperty("response_output")
  private T responseOutput;
}
