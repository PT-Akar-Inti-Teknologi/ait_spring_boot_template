package org.ait.project.guideline.example.modules.order.model.jpa.repository;

import java.util.Optional;
import org.ait.project.guideline.example.modules.order.model.jpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

  Optional<Order> findById(String id);


}
