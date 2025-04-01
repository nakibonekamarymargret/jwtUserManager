package com.AUTH.jwtUserManager.security;

import io.jsonwebtoken.*;
import java.util.Date;

import static io.jsonwebtoken.Jwts.*;

public class JwtUtil {
    private static final String SECRET_KEY = "mysecret12345rtkey";

    // Generate JWT Token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Extract username from JWT Token
    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    // Validate JWT Token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
