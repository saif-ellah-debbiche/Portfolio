package org.example.portfolio.conf;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.portfolio.entities.JwtTokenObject;
import org.example.portfolio.entities.Owner;
import org.example.portfolio.enums.TokenType;
import org.example.portfolio.repos.JwtTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Autowired private JwtTokenRepo jwtTokenRepo;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Owner user, TokenType tokenType, int expiredInMinutes, Map<String, Object> tokenDetails) {
        tokenDetails.put("tokenType", tokenType.toString());
        return generateTokenWithSpecificExpiration(user, tokenDetails, 1000L * 60 * expiredInMinutes);

    }
    public String generateToken(Owner user,TokenType tokenType,int expiredInMinutes) {
        Map<String, Object> tokenDetails = new HashMap<>();
        tokenDetails.put("tokenType", tokenType.toString());;

        return generateTokenWithSpecificExpiration(user, tokenDetails, 1000L * 60 * expiredInMinutes);

    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public JwtTokenObject generateJWTRefreshToken(Owner user){
        //will expire in one weak
        var refreshToken = generateRefreshToken(user);


        LocalDateTime now = LocalDateTime.now();
        // Add one week to the current date
        LocalDateTime oneWeekLater = now.plus(1, ChronoUnit.WEEKS);

        return JwtTokenObject.builder()
                .revoked(false)
                .owner(user)
                .expired(false)
                .uselessIn(oneWeekLater)
                .value(refreshToken)
                .tokenType(TokenType.REFRESH_TOKEN)
                .build();
    }
    public String generateTokenWithSpecificExpiration(
            UserDetails userDetails,
            Map<String,Object> tokenDetails,
            Long expiration
    ) {
        return buildToken(tokenDetails, userDetails, expiration);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        System.out.println("Reaching here 2 ----");
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        //if the access token exist so it is in the black list
        JwtTokenObject jwtTokenObject= jwtTokenRepo.findByValue(token);
        return jwtTokenObject==null && isTokenValid(token,userDetails) ;
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        System.out.println(token);
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenObject save(JwtTokenObject jwtTokenObject) {
        return jwtTokenRepo.save(jwtTokenObject);
    }
    public void revokeOtherJWTRefreshTokens(Owner user) {
        jwtTokenRepo.revokeRefreshTokens(user, TokenType.REFRESH_TOKEN);
    }
}
