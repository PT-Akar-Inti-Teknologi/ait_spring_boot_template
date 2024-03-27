package org.ait.project.guideline.example.modules.order.service.adapter.command.impl;

import lombok.RequiredArgsConstructor;

import org.ait.project.guideline.example.modules.order.exception.copy.OrderNotFoundException;
import org.ait.project.guideline.example.modules.order.model.jpa.entity.Order;
import org.ait.project.guideline.example.modules.order.model.jpa.repository.OrderRepository;
import org.ait.project.guideline.example.modules.order.service.adapter.command.OrderCommandAdapter;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class OrderCommandAdapterImpl implements OrderCommandAdapter {

    private final OrderRepository orderRepository;

    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }
    
    @Override
    public Order submitOrder(String id, String status) {
    	Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    	order.setStatus(status);
        return orderRepository.save(order);
    }


}
