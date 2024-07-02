package org.ait.project.guideline.example.modules.order.service.adapter.command;

import org.ait.project.guideline.example.modules.order.model.jpa.entity.Order;


public interface OrderCommandAdapter {

  Order addOrder(Order order);

  Order submitOrder(String id, String status);


}
