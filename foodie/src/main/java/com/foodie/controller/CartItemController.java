package com.foodie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodie.model.Cart;
import com.foodie.service.CartItemService;

@RestController
@RequestMapping("/cart-item")
@CrossOrigin("*")
public class CartItemController {
	
	@Autowired
    private CartItemService cartItemService;

   
    @PutMapping("/update/{cartItemId}")
    public Cart updateQuantity(@PathVariable Long cartItemId,
                               @RequestParam String action) {
        return cartItemService.updateQuantity(cartItemId, action);
    }

 
    @DeleteMapping("/remove/{cartItemId}")
    public Cart removeItem(@PathVariable Long cartItemId) {
        return cartItemService.removeItem(cartItemId);
    }

}
