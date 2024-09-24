package org.ait.project.guideline.example.modules.product.service.adapter.query;

import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductQueryAdapter {

    List<Product> getAllProduct();
}
