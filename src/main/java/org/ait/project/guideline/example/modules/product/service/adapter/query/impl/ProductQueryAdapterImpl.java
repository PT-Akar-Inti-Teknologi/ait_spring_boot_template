package org.ait.project.guideline.example.modules.product.service.adapter.query.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;
import org.ait.project.guideline.example.modules.product.model.jpa.repository.ProductRepository;
import org.ait.project.guideline.example.modules.product.service.adapter.query.ProductQueryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductQueryAdapterImpl implements ProductQueryAdapter {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
