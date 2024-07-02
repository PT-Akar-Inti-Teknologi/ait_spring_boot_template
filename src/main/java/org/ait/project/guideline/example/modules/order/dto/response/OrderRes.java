package org.ait.project.guideline.example.modules.order.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ait.project.guideline.example.modules.customer.model.jpa.entity.Customer;
import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;


@Data
public class OrderRes {

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("productId")
  private Product productId;

  @JsonProperty("customerId")
  private Customer customerId;

  @JsonProperty("status")
  private String status;

}
