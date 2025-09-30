package com.foodie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.foodie.model.Product;
import com.foodie.repository.ProductRepository;

@Service
public class ProductService {
	
	
	 @Autowired
	    private ProductRepository productRepository;

	    
	    public List<Product> getAllProducts() {
	        return productRepository.findAll();
	    }

	 
	    public Optional<Product> getProductById(Long id) {
	        return productRepository.findById(id);
	    }

	    
	    public Product addProduct(Product product) {
	        return productRepository.save(product);
	    }

	   
	    public String updateProduct(Long id, Product updatedProduct) {
	        Optional<Product> productOpt = productRepository.findById(id);
	        if(productOpt.isEmpty()) return "Product not found!";

	        Product product = productOpt.get();
	        product.setName(updatedProduct.getName());
	        product.setPrice(updatedProduct.getPrice());
	        product.setImg(updatedProduct.getImg());
	        productRepository.save(product);
	        return "Product updated successfully!";
	    }

	   
	    public String deleteProduct(Long id) {
	        Optional<Product> productOpt = productRepository.findById(id);
	        if(productOpt.isEmpty()) return "Product not found!";
	        productRepository.delete(productOpt.get());
	        return "Product deleted successfully!";
	    }
}
