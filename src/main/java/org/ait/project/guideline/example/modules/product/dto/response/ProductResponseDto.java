package org.ait.project.guideline.example.modules.product.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private String name;
    private Integer qty;
    private BigDecimal price;
}
