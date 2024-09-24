package org.ait.project.guideline.example.modules.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    @NotBlank(message = "{productName.required}")
    @JsonProperty("productName")
    private String productName;

    @NotBlank(message = "{productQty.required}")
    @JsonProperty("qty")
    private Integer qty;

    @JsonProperty("price")
    private BigDecimal price;
}
