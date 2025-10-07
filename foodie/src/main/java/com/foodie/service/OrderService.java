package com.foodie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.foodie.dto.OrderRequest;
import com.foodie.model.CartItem;
import com.foodie.model.Order;
import com.foodie.model.OrderItem;
import com.foodie.model.Product;
import com.foodie.model.User;
import com.foodie.repository.CartItemRepository;
import com.foodie.repository.OrderItemRepository;
import com.foodie.repository.OrderRepository;
import com.foodie.repository.ProductRepository;
import com.foodie.repository.UserRepository;

@Service
public class OrderService {
	
	
	 @Autowired
	    private OrderRepository orderRepository;
	 
	 @Autowired
	 private UserRepository userRepository;

	 @Autowired
	 private ProductRepository productRepository;

	 @Autowired
	 private CartItemRepository cartItemRepository;
	 
	 @Autowired
	    private OrderItemRepository orderItemRepository;
	 
	

	   
	 public Order placeOrder(Long userId,OrderRequest orderRequest) {
		 
		    User user = userRepository.findById(userId)
		            .orElseThrow(() -> new RuntimeException("User not found"));

		   
		    Order order = new Order();
		    order.setUser(user);
		    order.setDeliveryAddress(orderRequest.getDeliveryAddress());
		    order.setStatus("PLACED");

		   
		    

		  
		    List<OrderItem> orderItems = orderRequest.getItems().stream().map(itemDto -> {
		        Product product = productRepository.findById(itemDto.getProductId())
		                .orElseThrow(() -> new RuntimeException("Product not found"));

		        OrderItem item = new OrderItem();
		        item.setOrder(order);            
		        item.setProduct(product);
		        item.setQuantity(itemDto.getQuantity());
		        item.setPrice(product.getPrice());
		        return item;
		    }).toList();

		   
//		    orderItemRepository.saveAll(orderItems);

		    
		    order.setItems(orderItems);

	
		    double subtotal = orderItems.stream()
		            .mapToDouble(i -> i.getPrice() * i.getQuantity())
		            .sum();
		    double deliveryCharge = 50;
		    double discount = 0;
		    double finalTotal = subtotal + deliveryCharge - discount;

		    order.setSubtotal(subtotal);
		    order.setDeliveryCharge(deliveryCharge);
		    order.setDiscount(discount);
		    order.setFinalTotal(finalTotal);

		   
		    return orderRepository.save(order);
		}

	    public List<Order> getAllOrders() {
	        return orderRepository.findAll();
	    }

	
	    public List<Order> getOrdersByUser(Long userId) {
	        return orderRepository.findByUserId(userId);
	    }

	   
	    public Optional<Order> getOrderById(Long id) {
	        return orderRepository.findById(id);
	    }

	 
	    public String deleteOrder(Long id) {
	        Optional<Order> orderOpt = orderRepository.findById(id);
	        if(orderOpt.isEmpty()) return "Order not found!";
	        orderRepository.delete(orderOpt.get());
	        return "Order deleted successfully!";
	    }

}
