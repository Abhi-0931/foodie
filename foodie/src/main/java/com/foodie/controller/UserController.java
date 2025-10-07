package com.foodie.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodie.dto.LoginRequest;
import com.foodie.dto.UserDTO;
import com.foodie.model.User;
import com.foodie.repository.UserRepository;
import com.foodie.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*") // allow only your frontend

public class UserController {
	
	 @Autowired
	    private UserService service;
	 
	 
	 @Autowired
	 private UserRepository userRepository;

	    @PostMapping("/signup")
	    public String signup(@RequestBody User user) {
	        return service.signup(user);
	    }
////
//	    @PostMapping("/signin")
//	    public String signin(@RequestBody LoginRequest loginRequest) {
//	        return service.signin(loginRequest.getEmail(), loginRequest.getPassword());
//	    }	
	    
	    @PostMapping("/signin")
	    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
	        User user = service.signin(loginRequest.getEmail(), loginRequest.getPassword());
	        
	        if (user != null) {
	            return ResponseEntity.ok(user); // returns JSON
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                                 .body("Invalid email or password");
	        }
	    }



	    
	   

	



	    @GetMapping
	    public List<User> getAllUsers() {
	        return service.getAllUsers();
	    }

	    @GetMapping("/{id}")
	    public Optional<User> getById(@PathVariable Long id) {
	        return service.getById(id);
	    }

	    @DeleteMapping("/{id}")
	    public String deleteUser(@PathVariable Long id) {
	        return service.deleteUser(id);
	    }
	
	

}
