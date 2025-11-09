package com.example.flightapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static Key SIGNING_KEY;
    private static long EXPIRATION_TIME;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        SIGNING_KEY = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    @Value("${jwt.expiration}")
    public void setExpiration(long expiration) {
        EXPIRATION_TIME = expiration;
    }

    public static String generateToken(String username, String role) {
        return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SIGNING_KEY, SignatureAlgorithm.HS256).compact();
    }

    public static String extractUsername(String token) {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build();
        Claims claims = (Claims)jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public static String extractRole(String token) {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build();
        Claims claims = (Claims)jwtParser.parseClaimsJws(token).getBody();
        return (String)claims.get("role", String.class);
    }

    public static boolean isAdmin(String token) {
        String role = extractRole(token);
        return "ADMIN".equals(role);
    }

    public static boolean isAdminOrStaff(String token) {
        String role = extractRole(token);
        return "ADMIN".equals(role) || "STAFF".equals(role);
    }
}
