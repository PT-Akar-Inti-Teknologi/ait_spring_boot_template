package org.ait.project.guideline.example.modules.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class ProductReq {

  @JsonProperty("quantity")
  private Integer quantity;
}
