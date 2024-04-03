package org.ait.project.guideline.example.modules.product.model.jpa.repository;

import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> , JpaSpecificationExecutor<Product>{

	@Query("select a from Product a where a.quantity > 0")
	List<Product> findByQuantity();
	
	Optional<Product> findById(String id);

}
