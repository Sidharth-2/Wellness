package com.sid.wellness.auth;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.wellness.dto.ApiError;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
        
        sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token", "");

    }
    
    private void sendError(HttpServletResponse response, int status, String message, String path) throws IOException {
        ApiError apiError = new ApiError(false, status, HttpStatus.valueOf(status).getReasonPhrase(), message, path);
        response.setContentType("application/json");
        response.setStatus(status);
        new ObjectMapper().writeValue(response.getOutputStream(), apiError);
    }

}
