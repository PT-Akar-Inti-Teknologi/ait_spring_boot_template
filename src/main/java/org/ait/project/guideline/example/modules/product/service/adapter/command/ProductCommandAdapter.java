package org.ait.project.guideline.example.modules.product.service.adapter.command;

import org.ait.project.guideline.example.modules.product.model.jpa.entity.Product;


public interface ProductCommandAdapter {
	
	 Product updateQuantity(String id);

   
}
