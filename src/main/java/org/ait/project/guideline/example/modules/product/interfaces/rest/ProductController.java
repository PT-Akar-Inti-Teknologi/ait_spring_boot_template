package org.ait.project.guideline.example.modules.product.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.modules.product.dto.request.ProductRequestDto;
import org.ait.project.guideline.example.modules.product.dto.response.ProductResponseDto;
import org.ait.project.guideline.example.modules.product.service.core.ProductCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "API Product CRUD")
@RequestMapping("/product")
@Slf4j
public class ProductController {
    private final ProductCore productCore;

    @Operation(summary = "API to get All Product")
    @GetMapping("/all")
    public ResponseEntity<ResponseTemplate<ResponseCollection<ProductResponseDto>>> getProducts() {
        return productCore.getAllProduct();
    }

    @Operation(summary = "API to get add Product")
    @PostMapping
    public ResponseEntity<ResponseTemplate<ResponseDetail<ProductResponseDto>>> saveProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        return productCore.insertProduct(requestDto);
    }


    @Operation(summary = "API to get update Product")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<ProductResponseDto>>> updateProduct(@PathVariable("id") Integer id,
                                                                                            @RequestBody ProductRequestDto requestDto) {
        return productCore.updateProduct(id, requestDto);
    }

    @Operation(summary = "API to get delete Product")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> updateProduct(@PathVariable("id") Integer id) {
        return productCore.deleteProduct(id);
    }

}
