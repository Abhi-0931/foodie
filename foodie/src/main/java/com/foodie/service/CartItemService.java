package com.foodie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodie.model.Cart;
import com.foodie.model.CartItem;
import com.foodie.repository.CartItemRepository;
import com.foodie.repository.CartRepository;

@Service
public class CartItemService {
	
	@Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

 
    public Cart updateQuantity(Long cartItemId, String action) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if ("increase".equals(action)) {
            item.setQuantity(item.getQuantity() + 1);
        } else if ("decrease".equals(action) && item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        }

        cartItemRepository.save(item);
        return item.getCart();
    }

    public Cart removeItem(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        return cartRepository.save(cart);
    }

}
