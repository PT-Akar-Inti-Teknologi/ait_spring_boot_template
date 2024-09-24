package org.ait.project.guideline.example.modules.product.model.jpa.repository;

import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
}
