package com.foodie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.foodie.dto.LoginRequest;
import com.foodie.model.User;
import com.foodie.repository.UserRepository;


@Service
public class UserService {
	
	
	@Autowired
    private UserRepository userRepository;

    public String signup(User user){
    	Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return "Email already registered!";
        }
        userRepository.save(user);
        return "Signup successful!";
    }

//    public String signin(String email, String password) {
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user.isEmpty()) {
//            return "No account found with this email!";
//        }
//        if (!user.get().getPassword().equals(password)) {
//            return "Incorrect password!";
//        }
//        return "Login successful!";
//    }
    
    public User signin(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user; // return full user object
            }
        }
        return null; // login failed
    }

    
    
  


    
   
    
    


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully!";
    }

}
