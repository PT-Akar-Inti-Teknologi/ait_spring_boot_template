package org.ait.project.guideline.example.modules.product.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.product.dto.response.ProductRes;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCoreImpl implements ProductCore {


  private final ResponseHelper responseHelper;

  private final ProductTransform productTransform;

  private final ProductQueryAdapter productQueryAdapter;

  private final ProductCommandAdapter productCommandAdapter;

  @Override
  public ResponseEntity<ResponseTemplate<ResponseCollection<ProductRes>>> getProduct() {
    return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, Page.empty(),
        productTransform.createProductResponseList(productQueryAdapter.findProduct()));

  }

  @Override
  @Transactional
  public ResponseEntity<ResponseTemplate<ResponseDetail<ProductRes>>> updateQuantity(String id) {
    Product updatedQuantity = productCommandAdapter.updateQuantity(id);
    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
        productTransform.createProductResponse(updatedQuantity));

  }

  @Override
  public ResponseEntity<ResponseTemplate<ResponseCollection<ProductRes>>> getProducts(String search,
                                                                                      String sort,
                                                                                      Integer pageNumber,
                                                                                      Integer pageSize) {
    Sort sortBy = Sort.by(sort).ascending();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy);
    return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, Page.empty(pageable),
        productTransform.createProductResponseList(
            productQueryAdapter.findProducts(search, sort, pageNumber, pageSize)));
  }

}
