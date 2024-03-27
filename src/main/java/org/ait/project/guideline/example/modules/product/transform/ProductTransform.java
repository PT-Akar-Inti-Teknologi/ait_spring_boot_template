package org.ait.project.guideline.example.modules.product.transform;

import org.ait.project.guideline.example.modules.product.dto.response.ProductRes;
import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductTransform {
	
	@IterableMapping(qualifiedByName = "createProductResponse")
    @Named("createProductResponseList")
    List<ProductRes> createProductResponseList(List<Product> product);

    @Named("createProductResponse")
    ProductRes createProductResponse(Product product);


}
