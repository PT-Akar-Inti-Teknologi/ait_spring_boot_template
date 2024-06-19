package org.ait.project.guideline.example.modules.product.service.adapter.command.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.product.exception.ProductNotFoundException;
import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;
import org.ait.project.guideline.example.modules.product.model.jpa.repository.ProductRepository;
import org.ait.project.guideline.example.modules.product.service.adapter.command.ProductCommandAdapter;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductCommandAdapterImpl implements ProductCommandAdapter {

  private final ProductRepository productRepository;

  @Override
  public Product updateQuantity(String id) {
    Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    product.setQuantity(product.getQuantity() - 1);
    return productRepository.save(product);
  }

}
