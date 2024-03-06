package org.ait.project.guideline.example.modules.product.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.ait.project.guideline.example.modules.product.dto.response.ProductRes;
import org.ait.project.guideline.example.modules.product.service.core.ProductCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "API Order CRUD")
@RequestMapping("/product")
public class ProductController {
    private final ProductCore productCore;
    
    
    @Operation(summary = "API to get all Product when quantity is not 0")
    @GetMapping("/getProduct")
    public ResponseEntity<ResponseTemplate<ResponseCollection<ProductRes>>> getProduct() {
        return productCore.getProduct();
    }
    
    @Operation(summary = "API to get update quantity product")
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<ProductRes>>> updateUser(@PathVariable("id") String id) {
        return productCore.updateQuantity(id);
    }

}
