package com.sid.AuthenticationJwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.AuthenticationJwt.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findById(String id);
    
}

