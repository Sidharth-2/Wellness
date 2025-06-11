package com.sid.wellness.auth;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.wellness.dto.ApiError;
import com.sid.wellness.service.HealthTipService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "$@$dfsdfsdf%#$%fsddfdsfssgdbakfbsakck2342sasaf#4242";
    
    @Autowired HealthTipService healthTipService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	
    	String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token", "");
            return;
        }

        String token = authHeader.substring(7);

        try {
        	
        	System.out.println(token);

            String username = extractUsername(token);
            
        	System.out.println(username);

            if (username != null) {
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (JwtException e) {
        	                       	            
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token", "");
       
            return;
        }

        chain.doFilter(request, response);
    }
    
    private void sendError(HttpServletResponse response, int status, String message, String path) throws IOException {
        ApiError apiError = new ApiError(status, HttpStatus.valueOf(status).getReasonPhrase(), message, path);
        response.setContentType("application/json");
        response.setStatus(status);
        new ObjectMapper().writeValue(response.getOutputStream(), apiError);
    }
    
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}

