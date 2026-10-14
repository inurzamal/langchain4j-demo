package com.nur.tool;

import com.nur.model.OrderEntity;
import com.nur.service.OrderService;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderTools {

    private final OrderService orderService;;

//    public OrderTools(OrderService orderService) {
//        this.orderService = orderService;
//    }

    @Tool("Get the current status and delivery information for an order")
    public OrderEntity getOrderStatus(String orderId) {
        System.out.println("TOOL CALLED -> getOrderStatus(" + orderId + ")");
        return orderService.getOrderStatus(orderId);
    }

    @Tool("Cancel an order")
    public OrderEntity cancelOrder(String orderId) {
        System.out.println("TOOL CALLED -> cancelOrder(" + orderId + ")");
        return orderService.cancelOrder(orderId);
    }


}
