package com.sid.AuthenticationJwt.security.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	
    private final String SECRET_KEY = "$@$dfsdfsdf%#$%fsddfdsfssgdbakfbsakck2342sasaf#4242"; // Store securely in env/config
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        String id;
        String username = "";

        if (principal instanceof CustomOAuth2User) {
            id = ((CustomOAuth2User) principal).getId();
            username = ((CustomOAuth2User) principal).getName();
        } else if (principal instanceof UserPrincipal) {
            id = ((UserPrincipal) principal).getId().toString();
            username = ((UserPrincipal) principal).getName();
        } else {
            throw new IllegalStateException("Unexpected authentication principal type: " + principal.getClass());
        }
        
        String token = getToken(id);

        System.out.println("OAuth2 login success for email: " + id);
        
        String redirectUrl = "http://localhost:3000/oauth2/redirect?token=" + token + "&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8);

        System.out.println(redirectUrl);
        
        getRedirectStrategy().sendRedirect(request, response, redirectUrl); 
        
    }
    
    
    
    public String getToken(String id) {
   	 return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
   	
   }
}

