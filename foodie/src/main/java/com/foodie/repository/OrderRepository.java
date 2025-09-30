package com.foodie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodie.model.Order;
import com.foodie.model.User;


public interface OrderRepository extends JpaRepository<Order, Long>{
	
	  // Get all orders for a user
	
	List<Order> findByUserId(Long userId);


}
