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

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    private final String SECRET_KEY = "$@$dfsdfsdf%#$%fsddfdsfssgdbakfbsakck2342sasaf#4242"; // Store securely in env/config

    public User registerUser(String userName, String rawPassword, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        User user = new User(userName, encodedPassword, email);
        
        userRepository.save(user);
        
        return user;
    }

    public String authenticateUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
        	        	
            throw new RuntimeException("Invalid credentials");
        }


        return getToken(email);
       
    }
    
    public ResponseEntity<?> getError(String errorMessage) {
    
        ApiError error = new ApiError(false, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
    }
    
    public String getToken(String email) {
    	 return Jwts.builder()
                 .setSubject(email)
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                 .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                 .compact();
    	
    }
}
