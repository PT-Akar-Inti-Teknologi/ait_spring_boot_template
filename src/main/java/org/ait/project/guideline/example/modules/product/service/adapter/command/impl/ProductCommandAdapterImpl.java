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
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        productRepository.findById(product.getId()).orElseThrow(ProductNotFoundException::new);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
    }
}
