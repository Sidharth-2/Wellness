package com.sid.AuthenticationJwt.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.AuthenticationJwt.dto.request.AuthRequest;
import com.sid.AuthenticationJwt.dto.request.LoginRequest;
import com.sid.AuthenticationJwt.dto.response.AuthResponse;
import com.sid.AuthenticationJwt.dto.response.UserResponse;
import com.sid.AuthenticationJwt.entity.User;
import com.sid.AuthenticationJwt.repository.UserRepository;
import com.sid.AuthenticationJwt.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserService userService;
        
    @Autowired
    private UserRepository userRepository;
    
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    	
        	
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        if (optionalUser.isPresent()) {
            
        	User user = optionalUser.get();

            UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail());             
                                    
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	        	
                return userService.getError("Invalid credentials");
            }                       
            
            return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse<>(true, "Login successful", userResponse, userService.getToken(loginRequest.getEmail()))
            );

        } else {
        	
            return userService.getError("User not found with email: " + loginRequest.getEmail());
            
        }
       
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthRequest signupRequest) {

    	if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new AuthResponse<>(false, "Email already exists", null, null)
            );
        }
    	
        User user =  userService.registerUser(signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getEmail());
        
        UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail());
               
        String token = userService.getToken(user.getEmail());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new AuthResponse<>(true, "Signup successful", userResponse, token)
        );
    	    
    }
}

