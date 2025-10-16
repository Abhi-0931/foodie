package com.foodie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodie.model.Product;
import com.foodie.model.Vendor;

public interface ProductRepository extends JpaRepository<Product, Long>{

	 List<Product> findByVendor(Vendor vendor);

}
