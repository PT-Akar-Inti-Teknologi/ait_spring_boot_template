package org.ait.project.guideline.example.modules.product.service.adapter.query;

import java.util.List;

import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;

public interface ProductQueryAdapter {

	List<Product> findProduct();
    
    List<Product> findProducts(String searchBy, String sortBy, Integer pageNumber, Integer pageSize);

//    Optional<UserAit> findUserById(Integer id);


}
