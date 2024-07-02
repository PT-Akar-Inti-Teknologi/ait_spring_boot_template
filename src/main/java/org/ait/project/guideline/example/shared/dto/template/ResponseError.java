package org.ait.project.guideline.example.shared.dto.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
  @JsonProperty("errors")
  private List<ErrorDetail> errorDetailList;
}
