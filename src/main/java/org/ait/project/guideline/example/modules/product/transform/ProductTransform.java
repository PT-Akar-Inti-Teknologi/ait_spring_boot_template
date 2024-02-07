package org.ait.project.guideline.example.modules.product.transform;

import org.ait.project.guideline.example.modules.product.dto.request.ProductRequestDto;
import org.ait.project.guideline.example.modules.product.dto.response.ProductResponseDto;
import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductTransform {
    @Named("createProductResponse")
    ProductResponseDto createProductResponse(Product product);

    @IterableMapping(qualifiedByName = "createProductResponse")
    List<ProductResponseDto> createProductResponseList(List<Product> products);

    @Mapping(source = "productName", target = "name")
    Product createProductFromRequest(ProductRequestDto requestDto);
}
