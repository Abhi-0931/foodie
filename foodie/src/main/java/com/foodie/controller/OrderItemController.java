package com.foodie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodie.model.OrderItem;
import com.foodie.service.OrderItemService;

@RestController
@RequestMapping("/order-items")
@CrossOrigin("*") 
public class OrderItemController {
	
	@Autowired
    private OrderItemService orderItemService;

    @PostMapping("/add/{orderId}/{productId}/{quantity}")
    public OrderItem addOrderItem(@PathVariable Long orderId,
                                  @PathVariable Long productId,
                                  @PathVariable int quantity) {
        return orderItemService.addOrderItem(orderId, productId, quantity);
    }

    @GetMapping("/{orderId}")
    public List<OrderItem> getItemsByOrder(@PathVariable Long orderId) {
        return orderItemService.getItemsByOrder(orderId);
    }

}
