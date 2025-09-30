package com.foodie.service;




import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodie.model.Cart;
import com.foodie.model.CartItem;
import com.foodie.model.Product;
import com.foodie.model.User;
import com.foodie.repository.CartItemRepository;
import com.foodie.repository.CartRepository;
import com.foodie.repository.ProductRepository;
import com.foodie.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
	@Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;

    public Cart getCartByUser(Long userId) {

    	return cartRepository.findByUser_Id(userId);
    }
    
    @Transactional
    public Cart addToCart(Long userId, Long productId, int quantity) {
       
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user)));


       
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(cart, product, quantity); 
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

       
        return cartRepository.save(cart);
    }


    public Cart updateQuantity(Long cartItemId, String action) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if ("increase".equals(action)) item.setQuantity(item.getQuantity() + 1);
        else if ("decrease".equals(action) && item.getQuantity() > 1) 
            item.setQuantity(item.getQuantity() - 1);

        cartItemRepository.save(item);
        return item.getCart();
    }

    public Cart removeFromCart(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        return cartRepository.save(cart);
    }

    public String clearCart(Long userId) {
        Cart cart = getCartByUser(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
        return "Cart cleared!";
    }
}
