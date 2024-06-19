package org.ait.project.guideline.example.shared.feignclient.reqres.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class ReqresListUserResponse {

  @JsonProperty("per_page")
  private int perPage;

  @JsonProperty("total")
  private int total;

  @JsonProperty("data")
  private List<DataItem> data;

  @JsonProperty("page")
  private int page;

  @JsonProperty("total_pages")
  private int totalPages;

  @JsonProperty("support")
  private Support support;
}