package com.fiapchallenge.garage.shared.jwt;

import com.fiapchallenge.garage.infra.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    @Value("${api.security.jwt.secret}")
    private String secret;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(buildKey())
                .build();
        return parser
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public JwtTokenVO generateToken(UserDetailsImpl user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getFullname());
        claims.put("user_id", user.getId());
        return createToken(claims, user.getUsername());
    }

    private JwtTokenVO createToken(Map<String, Object> claims, String subject) {
        final Date issuedAt = new Date();

        final int expirationInHours = 10;
        java.util.Calendar expiration = Calendar.getInstance();
        expiration.setTime(issuedAt);
        expiration.add(Calendar.HOUR, expirationInHours);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration.getTime())
                .signWith(buildKey(), SignatureAlgorithm.HS256)
                .compact();
        return new JwtTokenVO(token, expiration.toInstant());
    }

    public boolean validateToken(String token, String username) {
        final String usernameFromToken = extractUsername(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token));
    }

    private Key buildKey() {
        return Keys.hmacShaKeyFor(this.secret.getBytes());
    }
}
