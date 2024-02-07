package org.ait.project.guideline.example.modules.product.service.core;

import org.ait.project.guideline.example.modules.product.dto.request.ProductRequestDto;
import org.ait.project.guideline.example.modules.product.dto.response.ProductResponseDto;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;

public interface ProductCore {

    ResponseEntity<ResponseTemplate<ResponseCollection<ProductResponseDto>>> getAllProduct();

    ResponseEntity<ResponseTemplate<ResponseDetail<ProductResponseDto>>> insertProduct(ProductRequestDto requestDto);

    ResponseEntity<ResponseTemplate<ResponseDetail<ProductResponseDto>>> updateProduct(Integer id, ProductRequestDto requestDto);

    ResponseEntity<ResponseTemplate<ResponseDetail<String>>> deleteProduct(Integer id);
}
