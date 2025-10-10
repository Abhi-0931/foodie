package com.foodie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodie.dto.AddCartRequest;
import com.foodie.model.Cart;
import com.foodie.service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
@PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")

public class CartController {
	@Autowired private CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCartByUser(userId);
    }
    @PostMapping("/add")

    public Cart addToCart(@RequestBody AddCartRequest request) {
        System.out.println("Add to cart request: " + request.getUserId() + ", " + request.getProductId());
        Cart cart = cartService.addToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        if (cart == null) {
            throw new RuntimeException("Cart service failed");
        }
        return cart;
    }


    @PutMapping("/update/{cartItemId}")
    public Cart updateQuantity(@PathVariable Long cartItemId, @RequestParam String action) {
        return cartService.updateQuantity(cartItemId, action);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public Cart removeItem(@PathVariable Long cartItemId) {
        return cartService.removeFromCart(cartItemId);
    }

    @DeleteMapping("/clear/{userId}")
    public String clearCart(@PathVariable Long userId) {
        return cartService.clearCart(userId);
    }
}
