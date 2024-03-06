package org.ait.project.guideline.example.modules.product.service.core;

import org.ait.project.guideline.example.modules.product.dto.response.ProductRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;


public interface ProductCore {
	
    ResponseEntity<ResponseTemplate<ResponseCollection<ProductRes>>> getProduct();
    
    ResponseEntity<ResponseTemplate<ResponseDetail<ProductRes>>> updateQuantity(String id);

}
