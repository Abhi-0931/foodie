package com.foodie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodie.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
