package com.foodie.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodie.dto.OrderRequest;
import com.foodie.model.Order;
import com.foodie.service.OrderService;



@RestController
@RequestMapping("/orders")
@CrossOrigin("*") 
@PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")

public class OrderController {
	
	 @Autowired
	    private OrderService orderService;

	  
	 @PostMapping("/place/{userId}")
	 public ResponseEntity<?> placeOrder(@PathVariable Long userId,@RequestBody OrderRequest orderRequest) {
	     try {
	         Order order = orderService.placeOrder(userId,orderRequest);
	         return ResponseEntity.ok(order);
	     } catch (Exception e) {
	         e.printStackTrace();   // <-- see error in console
	         return ResponseEntity.status(500).body(e.getMessage());
	     }
	 }



	    
	    @GetMapping
	    public List<Order> getAllOrders() {
	        return orderService.getAllOrders();
	    }

	    
	    @GetMapping("/user/{userId}")
	    public List<Order> getOrdersByUser(@PathVariable("userId") Long userId) {
	        return orderService.getOrdersByUser(userId);
	    }

	   
	    @GetMapping("/{id}")
	    public Optional<Order> getOrderById(@PathVariable Long id) {
	        return orderService.getOrderById(id);
	    }

	  
	    @DeleteMapping("/delete/{id}")
	    public String deleteOrder(@PathVariable Long id) {
	        return orderService.deleteOrder(id);
	    }
}
