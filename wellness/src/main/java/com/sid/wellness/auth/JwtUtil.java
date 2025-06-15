package com.sid.wellness.auth;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "$@$dfsdfsdf%#$%fsddfdsfssgdbakfbsakck2342sasaf#4242";

    public String extractUserId(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
        
}

