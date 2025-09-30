package com.foodie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodie.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	 // Find by email (used in login/signin)
	
	Optional<User> findByEmail(String email);

}
