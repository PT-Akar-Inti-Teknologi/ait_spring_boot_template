package org.ait.project.guideline.example.shared.dto.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationConfig {
  @JsonProperty("page")
  private int page;

  @JsonProperty("size")
  private int size;

  @JsonProperty("total")
  private long total;

  @JsonProperty("total_pages")
  private int totalPages;
}