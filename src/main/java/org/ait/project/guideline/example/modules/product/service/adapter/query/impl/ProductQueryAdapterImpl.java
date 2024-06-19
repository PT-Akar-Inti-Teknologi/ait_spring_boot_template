package org.ait.project.guideline.example.modules.product.service.adapter.query.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;
import org.ait.project.guideline.example.modules.product.model.jpa.repository.ProductRepository;
import org.ait.project.guideline.example.modules.product.service.adapter.query.ProductQueryAdapter;
import org.ait.project.guideline.example.utils.specification.MainSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryAdapterImpl implements ProductQueryAdapter {

  private final ProductRepository productRepository;
  private final MainSearch mainSearch;

  @Override
  public List<Product> findProduct() {
    return productRepository.findByQuantity();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Product> findProducts(String searchBy, String sortBy, Integer pageNumber,
                                    Integer pageSize) {
    Sort sort = Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<Product> data =
        productRepository.findAll(Specification.where(mainSearch.build(searchBy, "product")),
            pageable);

    return data.getContent();
  }

}
