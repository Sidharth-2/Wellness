package com.sid.AuthenticationJwt.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users_authentication")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private String username;

    private String password;
  
    @Column(unique = true)
    private String email;
    
    private String provider;

}
