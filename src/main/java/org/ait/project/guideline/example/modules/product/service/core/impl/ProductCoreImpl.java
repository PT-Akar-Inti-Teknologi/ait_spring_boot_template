package org.ait.project.guideline.example.modules.product.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.product.dto.request.ProductRequestDto;
import org.ait.project.guideline.example.modules.product.dto.response.ProductResponseDto;
import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;
import org.ait.project.guideline.example.modules.product.service.adapter.command.ProductCommandAdapter;
import org.ait.project.guideline.example.modules.product.service.adapter.query.ProductQueryAdapter;
import org.ait.project.guideline.example.modules.product.service.core.ProductCore;
import org.ait.project.guideline.example.modules.product.transform.ProductTransform;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCoreImpl implements ProductCore {
    private final ProductQueryAdapter productQueryAdapter;
    private final ProductCommandAdapter productCommandAdapter;
    private final ResponseHelper responseHelper;
    private final ProductTransform productTransform;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<ProductResponseDto>>> getAllProduct() {
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, Page.empty(),
                productTransform.createProductResponseList(productQueryAdapter.getAllProduct()));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<ProductResponseDto>>> insertProduct(ProductRequestDto requestDto) {
        Product product = productTransform.createProductFromRequest(requestDto);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
                productTransform.createProductResponse(productCommandAdapter.saveProduct(product)));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<ProductResponseDto>>> updateProduct(Integer id, ProductRequestDto requestDto) {
        Product product = productTransform.createProductFromRequest(requestDto);
        product.setId(id);

        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, productTransform.createProductResponse(productCommandAdapter.updateProduct(product)));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> deleteProduct(Integer id) {
        productCommandAdapter.deleteProduct(id);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, "Success");
    }
}
