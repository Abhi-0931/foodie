package com.foodie.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.foodie.model.User;
import com.foodie.repository.UserRepository;
import com.foodie.security.CustomUserDetailsService;
import com.foodie.security.JwtUtil;

import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")

public class AuthController {
	
	 @Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    @Autowired
	    private JwtUtil jwtUtil;
	    @Autowired
	    private CustomUserDetailsService userDetailsService;

	    // Signup
	    @PostMapping("/signup")
	    public String signup(@RequestBody User user) {
	        Optional<User> existing = userRepository.findByEmail(user.getEmail());
	        if (existing.isPresent()) {
	            return "Email already registered!";
	        }
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userRepository.save(user);
	        return "User registered successfully!";
	    }

	    // Signin
	    @PostMapping("/signin")
	    public ResponseEntity<?> signin(@RequestBody User loginRequest) {
	        Optional<User> dbUser = userRepository.findByEmail(loginRequest.getEmail());
	        if (dbUser.isEmpty()) {
	            return ResponseEntity.status(401)
	                    .body(Map.of("error", "Invalid email or password"));
	        }

	        
	        if (!passwordEncoder.matches(loginRequest.getPassword(), dbUser.get().getPassword())) {
	            return ResponseEntity.status(401)
	                    .body(Map.of("error", "Invalid email or password"));
	        }

	       
	        String token = jwtUtil.generateTokenByEmail(dbUser.get().getEmail());
	        String refreshToken = jwtUtil.generateRefreshToken(dbUser.get().getEmail());
	        User user = dbUser.get();
	        System.out.println("Generated JWT: " + token);

	        return ResponseEntity.ok(Map.of(
	                "Token", token,
	                "refreshToken", refreshToken,
	                "currentUser", user
	        ));
	    }
	    @PostMapping("/token")
	    public ResponseEntity<?> provideToken(@RequestHeader("Authorization") String authHeader) {
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            return ResponseEntity.status(401).body(Map.of("error", "Missing or invalid token"));
	        }

	        String token = authHeader.substring(7);
	        String email;

	        try {
	            email = jwtUtil.extractEmail(token);
	        } catch (Exception e) {
	            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
	        }

	        // Check if token is expired
	        if (jwtUtil.isTokenExpired(token)) {
	            return ResponseEntity.status(401).body(Map.of("error", "Token expired"));
	        }

	        String newToken = jwtUtil.generateTokenByEmail(email);

	        return ResponseEntity.ok(Map.of("token", newToken));
	    }
	    @PostMapping("/refresh-token")
	    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> body) {
	        String refreshToken = body.get("refreshToken");

	        if (refreshToken == null) {
	            return ResponseEntity.status(400).body(Map.of("error", "Missing refresh token"));
	        }

	        try {
	            String email = jwtUtil.extractEmail(refreshToken);

	            // Check if refresh token is expired
	            if (jwtUtil.isTokenExpired(refreshToken)) {
	                return ResponseEntity.status(401).body(Map.of("error", "Refresh token expired. Please login again."));
	            }

	            // Generate new access token
	            String newAccessToken = jwtUtil.generateTokenByEmail(email);
	            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));

	        } catch (Exception e) {
	            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
	        }
	    }




}
