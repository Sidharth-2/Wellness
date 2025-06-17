package com.sid.AuthenticationJwt.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sid.AuthenticationJwt.dto.ApiError;
import com.sid.AuthenticationJwt.dto.request.LoginRequest;
import com.sid.AuthenticationJwt.dto.response.AuthResponse;
import com.sid.AuthenticationJwt.entity.User;
import com.sid.AuthenticationJwt.repository.UserRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public User registerUser(String userName, String rawPassword, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        User user = new User(generateUniqueLongId(), userName, encodedPassword, email, "JWT");
        
        userRepository.save(user);
        
        return user;
    }
    
    public ResponseEntity<?> getError(String errorMessage) {
    
        ApiError error = new ApiError(false, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);        
    }
        
    public User getId(String email) {
		
    	Optional<User> optionalUser = userRepository.findByEmail(email);
    	
        if (optionalUser.isPresent()) {
            
        	User user = optionalUser.get();
        	
        	return user;
        	
        } else {
			return null;
		}
	}
    
    public String generateUniqueLongId() {
        return String.valueOf(System.currentTimeMillis() + new Random().nextInt(1000)); // 13-14 digits
    }
}
