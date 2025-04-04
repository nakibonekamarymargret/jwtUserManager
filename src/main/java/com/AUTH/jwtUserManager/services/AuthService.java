package com.AUTH.jwtUserManager.services;

import com.AUTH.jwtUserManager.models.Users;
import com.AUTH.jwtUserManager.security.JwtUtil;
import com.AUTH.jwtUserManager.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final JwtUtil jwtutil;
    private final UserRepository userRepo;

    public AuthService(AuthenticationManager authManager, JwtUtil jwtutil, UserRepository userRepo) {
        this.authManager = authManager;
        this.jwtutil = jwtutil;
        this.userRepo = userRepo;
    }

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtutil.generateToken(new HashMap<>(), 1000 * 60 * 60 * 24, user.getUsername());
        }
        return "User not authenticated";
    }

}
