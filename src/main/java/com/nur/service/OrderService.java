package com.nur.service;

import com.nur.model.OrderEntity;
import com.nur.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity getOrderStatus(String orderId) {

        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found: " + orderId));
    }

    public OrderEntity cancelOrder(String orderId) {
        OrderEntity order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found: " + orderId));
        if (order.getStatus().equalsIgnoreCase("Shipped")) {
            throw new RuntimeException("Cannot cancel an order that has already been shipped.");
        }
        order.setStatus("CANCELLED");
        order.setExpectedDelivery(null);
        return orderRepository.save(order); // update the order in the database
    }
}