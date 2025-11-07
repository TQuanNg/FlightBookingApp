package com.example.flightapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final Key SIGNING_KEY;
    private static final long EXPIRATION_TIME = 36000000L;

    public static String generateToken(String username, String role) {
        return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 36000000L)).signWith(SIGNING_KEY, SignatureAlgorithm.HS256).compact();
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

    static {
        SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}

