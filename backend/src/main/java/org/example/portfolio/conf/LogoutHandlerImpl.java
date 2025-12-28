package org.example.portfolio.conf;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.portfolio.entities.JwtTokenObject;
import org.example.portfolio.repos.JwtTokenRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@AllArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {
    private final JwtTokenRepo tokenRepository;

    private final JwtService jwtService;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        accessToken = authHeader.substring(7);
        try{
            Claims claims=jwtService.extractAllClaims(accessToken);
            tokenRepository.revokeTokenByUserEmail(claims.getSubject());
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // ✅ Fix: Add CORS headers
            response.setHeader("Access-Control-Allow-Origin", "*"); // Allow all origins (change if needed)
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            String jsonResponse = String.format("{\"message\": \"%s\"}", "logout has been successful");
            response.getWriter().write(jsonResponse);
        }catch (ExpiredJwtException e){
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // ✅ Fix: Add CORS headers
            response.setHeader("Access-Control-Allow-Origin", "*"); // Allow all origins (change if needed)
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            String jsonResponse = String.format("{\"message\": \"%s\"}", "your refresh token has been expired");
            try{
                response.getWriter().write(jsonResponse);
            }catch (IOException ex){
                System.out.println("something went wrong ex");
            }
        }catch(IOException e){
            System.out.println("something went wrong e");
        }


    }
}
