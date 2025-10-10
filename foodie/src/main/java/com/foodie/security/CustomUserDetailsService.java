package com.foodie.security;

import org.springframework.stereotype.Service;

import com.foodie.repository.UserRepository;

import java.util.Collections;

import com.foodie.model.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	  @Autowired
	    private UserRepository userRepository;

	
	 @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

	        return new org.springframework.security.core.userdetails.User(
	                user.getEmail(),
	                user.getPassword(),
	                Collections.singleton(() -> "ROLE_" + user.getRole())
	        );
	    }

}
