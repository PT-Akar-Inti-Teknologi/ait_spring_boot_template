package org.ait.project.guideline.example.shared.dto.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
