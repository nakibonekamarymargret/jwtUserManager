package com.AUTH.jwtUserManager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

    private String secretKey = "";

    public JwtUtil() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token) { // Extract all claims from the token
        return Jwts.parser()
                .verifyWith(getKey()) // Use the signing key
                .build() // Build the parser
                .parseSignedClaims(token) // Parse the token
                .getPayload(); // Get the body of the token in the form of claims. claims are key-value pairs
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) { // Extract claims from the token

        // try to make it that claims are not extracted if token is expired //

        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimsResolver.apply(claims); // Return the claims as a function of the claims resolver
    }

    public String extractUsername(String token) { // Extract the username from the token
        return extractClaims(token, Claims::getSubject); // Return the subject of the claims
    }
    public String generateToken(Map<String, Object> claims, long expirationTimeMillis, String username) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();
    }
    public String generateAccessToken(String username) { // Generate an access token for the user
        // Access token valid for 5 minutes
        return generateToken(new HashMap<>(), 1000 * 60 * 60 * 24, username); // Generate a token with the user's details
    }

    public String generateRefreshToken(String username) { // Generate a refresh token for the user
        // Refresh token valid for 3 days
        return generateToken(new HashMap<>(), 1000 * 60 * 60 * 24 * 3, username); // Generate a token with the user's details
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extract the username from the token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Date extractExpiration(String token) { // Extract the expiration date from the token
        return extractClaims(token, Claims::getExpiration); // Return the expiration date of the claims
    }

    private Boolean isTokenExpired(String token) { // Check if the token is expired
        return extractExpiration(token).before(new Date()); // Return true if the expiration date is before the current date
    }

}

